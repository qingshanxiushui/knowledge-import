package com.knowledge.Application;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.knowledge.dto.DiagnosisDto;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.PositiveSymptomReusltDto;
import com.knowledge.entity.DiagnosticFactorEntity;
import com.knowledge.entity.DiagnosticFactorsEntity;
import com.knowledge.entity.OperationEntity;
import com.knowledge.entity.ScaleEntity;
import com.knowledge.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.UserEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static HashMap<String, DiagnosisDto> diagnosisMap = new HashMap<String,DiagnosisDto>();
    public static List<DiagnosisResultDto> diagnosisResultList = new ArrayList<DiagnosisResultDto>();
    public static int serialNoExcel = 0;


    public static void main( String[] args ) throws IOException {

        //诊断因素
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素","诊断因素");
        //printDiagnosisMap();
        //手术
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术","手术");
        //printDiagnosisMap();
        //System.out.println(diagnosisMap.get("lambert-eaton肌无力综合征"));
        //量表
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-量表","量表");
        //printDiagnosisMap();
        //System.out.println(diagnosisMap.get("阿尔茨海默病"));

        /*DiagnosisDto diagnosis= diagnosisMap.get("阿尔茨海默病");
        diagnosisFlatMap(diagnosis);
        System.out.println(diagnosisResultList.toString());*/

        for(String key:diagnosisMap.keySet()){
            diagnosisFlatMap(diagnosisMap.get(key));
        }

        //生成excel文档
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("诊断","临床表现"),
                DiagnosisResultDto.class, diagnosisResultList);
        FileOutputStream fos = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\diagnosis-export-b.xls");
        workbook.write(fos);
        fos.close();
    }

    private static void diagnosisFlatMap(DiagnosisDto diagnosis) {
        //打平阳性症状
        String positiveSymptomContent;
        String positiveSymptomName;
        List<PositiveSymptomReusltDto> positiveSymptomReusltList= new ArrayList<PositiveSymptomReusltDto>();
        if(diagnosis.getPositiveSymptoms() !=null && !diagnosis.getPositiveSymptoms().isEmpty()){
            for(DiagnosticFactorEntity diagnosticFactorEntity: diagnosis.getPositiveSymptoms()){
                positiveSymptomContent = diagnosticFactorEntity.getDesc();
                if(diagnosticFactorEntity.getAnnotate_symptom()!=null && !diagnosticFactorEntity.getAnnotate_symptom().isEmpty()){
                    for (String annotate_symptom :diagnosticFactorEntity.getAnnotate_symptom()){
                        positiveSymptomName = annotate_symptom;
                        PositiveSymptomReusltDto positiveSymptomReuslt = new PositiveSymptomReusltDto(positiveSymptomName,positiveSymptomContent);
                        positiveSymptomReusltList.add(positiveSymptomReuslt);
                    }
                }
            }
        }

        //构建输出结果
        int operationSize = (diagnosis.getOperationsName() == null || diagnosis.getOperationsName().isEmpty())?0: diagnosis.getOperationsName().size();
        int scaleSize= (diagnosis.getScaleContent() == null || diagnosis.getScaleContent().isEmpty())?0: diagnosis.getScaleContent().size();
        int positiveSymptomSize= (positiveSymptomReusltList == null || positiveSymptomReusltList.isEmpty())?0:positiveSymptomReusltList.size();

        int serialNo = 0;
        for(serialNo =0;serialNo < Math.max(operationSize,Math.max(scaleSize,positiveSymptomSize)); serialNo++){
            DiagnosisResultDto diagnosisResultDto = new DiagnosisResultDto();
            if(serialNo==0){
                serialNoExcel = serialNoExcel+1;
                diagnosisResultDto.setTermName(diagnosis.getTermName());
                diagnosisResultDto.setSerialNo(String.valueOf(serialNoExcel));
            }
            if(serialNo<operationSize){
                diagnosisResultDto.setAssociatedSurgeryName(diagnosis.getOperationsName().get(serialNo));
            }
            if(serialNo<scaleSize){
                diagnosisResultDto.setAssessmentScaleName(diagnosis.getScaleContent().get(serialNo).getName());
                diagnosisResultDto.setAssessmentScaleContent(diagnosis.getScaleContent().get(serialNo).getContent());
            }
            if(serialNo<positiveSymptomSize){
                diagnosisResultDto.setPositiveSymptomName(positiveSymptomReusltList.get(serialNo).getName());
                diagnosisResultDto.setPositiveSymptomContent(positiveSymptomReusltList.get(serialNo).getDesc());
                diagnosisResultDto.setPositiveSymptomSource("BMJ");
            }
            diagnosisResultList.add(diagnosisResultDto);
        }
    }

    private static void printDiagnosisMap() {
        System.out.println(diagnosisMap.size());
        int i=0;
        for(Map.Entry<String,DiagnosisDto> entry :diagnosisMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
            i++;
            if(i>=3){
                break;
            }
        }
    }

    public static void fileList(String filepath,String fileType) {
        File file= new File(filepath);
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if(file2.isDirectory()) {
                fileList(file2 + "\\",fileType);
            }
            if(!file2.isDirectory()) {
                String fileName = file2.getName();
                if(!fileName.contains("emr") && !fileName.contains("DS_Store") && !fileName.contains("graph.txt")){
                    //System.out.println(file2);
                    String jsonStr = FileUtil.readJsonFile(file2.toString());
                    switch(fileType){
                        case "诊断因素":
                            diagnosticProcess(jsonStr);
                            break;
                        case "手术":
                            operationProcess(jsonStr);
                            break;
                        case "量表":
                            scaleProcess(jsonStr);
                            break;
                        default:
                            System.out.println("default");
                    }

                }
            }
        }
    }

    private static void diagnosticProcess(String jsonStr) {
        //System.out.println(jsonStr);
        DiagnosticFactorsEntity diagnosic = JSON.parseObject(jsonStr,  DiagnosticFactorsEntity.class);
        DiagnosisDto diagnosis= diagnosisMap.get(diagnosic.getDisease());
        if(diagnosis!=null) { //不为空
            diagnosis.setPositiveSymptoms(diagnosic.getFactor());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }else{ //null
            DiagnosisDto diagnosisNew = new DiagnosisDto();
            diagnosisNew.setTermName(diagnosic.getDisease());
            diagnosisNew.setPositiveSymptoms(diagnosic.getFactor());
            diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
        }
    }
    private static void operationProcess(String jsonStr) {
        //System.out.println(jsonStr);
        OperationEntity operation = JSON.parseObject(jsonStr,  OperationEntity.class);
        DiagnosisDto diagnosis= diagnosisMap.get(operation.getDisease());
        if(diagnosis!=null) { //不为空
            diagnosis.setOperationsName(operation.getOperations());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }else{ //null
            DiagnosisDto diagnosisNew = new DiagnosisDto();
            diagnosisNew.setTermName(operation.getDisease());
            diagnosisNew.setOperationsName(operation.getOperations());
            diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
        }
    }
    private static void scaleProcess(String jsonStr) {
        //System.out.println(jsonStr);
        ScaleEntity scale = JSON.parseObject(jsonStr,  ScaleEntity.class);
        DiagnosisDto diagnosis= diagnosisMap.get(scale.getDisease());
        if(diagnosis!=null) { //不为空
            diagnosis.setScaleContent(scale.getList());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }else{ //null
            DiagnosisDto diagnosisNew = new DiagnosisDto();
            diagnosisNew.setTermName(scale.getDisease());
            diagnosisNew.setScaleContent(scale.getList());
            diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
        }
    }
}
