package com.knowledge.Application;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSON;
import com.knowledge.dto.DiagnosisDto;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.DifferentialDiagnosisResultDto;
import com.knowledge.dto.PositiveSymptomReusltDto;
import com.knowledge.entity.*;
import com.knowledge.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BmjApp {

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
        //鉴别诊断
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-鉴别诊断","鉴别诊断");
        //printDiagnosisMap();
        //System.out.println(diagnosisMap.get("addison病"));
        //检查
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-检查","检查");
        //printDiagnosisMap();
        //System.out.println(diagnosisMap.get("addison病"));

        /*DiagnosisDto diagnosis= diagnosisMap.get("addison病");
        System.out.println(diagnosis.toString());
        diagnosisFlatMap(diagnosis);
        System.out.println(diagnosisResultList.toString());*/

        //数据处理
        System.out.println(diagnosisMap.size());
        for(String key:diagnosisMap.keySet()){
            diagnosisFlatMap(diagnosisMap.get(key));
        }

        //生成excel文档
        exportLocalExcel();
    }

    private static void exportLocalExcel() throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("诊断","临床表现", ExcelType.XSSF),
                DiagnosisResultDto.class, diagnosisResultList);
        FileOutputStream fos = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\diagnosis-export.xlsx");
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
        //System.out.println(positiveSymptomReusltList.toString());

        //打平鉴别诊断
        String differentialDiagnosisName;
        String differentialDiagnosisPerformance;
        List<DifferentialDiagnosisResultDto> differentialDiagnosisReusltList= new ArrayList<DifferentialDiagnosisResultDto>();
        if(diagnosis.getDifferentialDiagnosis() !=null && !diagnosis.getDifferentialDiagnosis().isEmpty()){
            for(DifferentialDiagnosisSubEntity differentialDiagnosisSubEntity:diagnosis.getDifferentialDiagnosis()){
                differentialDiagnosisName = differentialDiagnosisSubEntity.get疾病();
                if(differentialDiagnosisSubEntity.get症状()!=null && !differentialDiagnosisSubEntity.get症状().isEmpty()){
                    for(String performance:differentialDiagnosisSubEntity.get症状()){
                        differentialDiagnosisPerformance = performance;
                        DifferentialDiagnosisResultDto differentialDiagnosisResult = new DifferentialDiagnosisResultDto(differentialDiagnosisName,differentialDiagnosisPerformance);
                        differentialDiagnosisReusltList.add(differentialDiagnosisResult);
                    }
                }
            }
        }
        //System.out.println(differentialDiagnosisReusltList.toString());

        //打平检查
        List<InspectSubItemEntity> positiveInspectResultList = null;
        if( diagnosis.getPositiveInspect()!=null && !diagnosis.getPositiveInspect().isEmpty()){
            positiveInspectResultList = diagnosis.getPositiveInspect().stream().flatMap(inspectSubEntity -> inspectSubEntity.getItems().stream()).collect(Collectors.toList());
        }
        //System.out.println(positiveInspectResultList);

        //构建输出结果
        int operationSize = (diagnosis.getOperationsName() == null || diagnosis.getOperationsName().isEmpty())?0: diagnosis.getOperationsName().size();
        int scaleSize = (diagnosis.getScaleContent() == null || diagnosis.getScaleContent().isEmpty())?0: diagnosis.getScaleContent().size();
        int positiveSymptomSize = (positiveSymptomReusltList.isEmpty())?0:positiveSymptomReusltList.size();
        int differentialDiagnosisSize = (differentialDiagnosisReusltList.isEmpty())?0:differentialDiagnosisReusltList.size();
        int positiveInspectSize = (positiveInspectResultList ==null || positiveInspectResultList.isEmpty())?0:positiveInspectResultList.size();
        int maxSize = Math.max(Math.max(Math.max(Math.max(operationSize,scaleSize),positiveSymptomSize),differentialDiagnosisSize),positiveInspectSize);

        int serialNo = 0;
        for(serialNo =0;serialNo < maxSize; serialNo++){
            DiagnosisResultDto diagnosisResultDto = new DiagnosisResultDto();
            if(serialNo==0){  //首记录设置序号1
                serialNoExcel = serialNoExcel+1;
                diagnosisResultDto.setTermName(diagnosis.getTermName());
                diagnosisResultDto.setSerialNo(String.valueOf(serialNoExcel));
            }
            if(serialNo<operationSize){ //手术
                diagnosisResultDto.setAssociatedSurgeryName(diagnosis.getOperationsName().get(serialNo));
                diagnosisResultDto.setAssociatedSurgerySource("BMJ");
            }
            if(serialNo<scaleSize){ //量表
                diagnosisResultDto.setAssessmentScaleName(diagnosis.getScaleContent().get(serialNo).getName());
                diagnosisResultDto.setAssessmentScaleContent(diagnosis.getScaleContent().get(serialNo).getContent());
                diagnosisResultDto.setAssessmentScaleSource("BMJ");
            }
            if(serialNo<positiveSymptomSize){ //阳性症状
                diagnosisResultDto.setPositiveSymptomName(positiveSymptomReusltList.get(serialNo).getName());
                diagnosisResultDto.setPositiveSymptomContent(positiveSymptomReusltList.get(serialNo).getDesc());
                diagnosisResultDto.setPositiveSymptomSource("BMJ");
            }
            if(serialNo<differentialDiagnosisSize){ //诊断鉴别
                diagnosisResultDto.setIdentifyDisease(differentialDiagnosisReusltList.get(serialNo).getName());
                diagnosisResultDto.setIdentifySymptom(differentialDiagnosisReusltList.get(serialNo).getPerformance());
                diagnosisResultDto.setIdentifySource("BMJ");
            }
            if(serialNo<positiveInspectSize){ //阳性检查
                diagnosisResultDto.setPositiveInspectAName(positiveInspectResultList.get(serialNo).getTitle());
                diagnosisResultDto.setPositiveInspectAResult(positiveInspectResultList.get(serialNo).getResult());
                diagnosisResultDto.setPositiveInspectAContent(positiveInspectResultList.get(serialNo).getDesc());
                diagnosisResultDto.setPositiveInspectASource("BMJ");
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

    private static void fileList(String filepath,String fileType) {
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
                        case "鉴别诊断":
                            differentialDiagnosisProcess(jsonStr);
                            break;
                        case "检查":
                            positiveInspectProcess(jsonStr);
                            break;
                        default:
                            System.out.println("default");
                    }

                }
            }
        }
    }

    private static void positiveInspectProcess(String jsonStr) {
        InspectEntity inspect = JSON.parseObject(jsonStr,  InspectEntity.class);
        if(inspect.get检查()!=null && !inspect.get检查().isEmpty()){
            String disease = inspect.getName().toLowerCase();
            DiagnosisDto diagnosis= diagnosisMap.get(disease);
            if(diagnosis!=null){ //不为空
                diagnosis.setPositiveInspect(inspect.get检查());
                diagnosisMap.put(diagnosis.getTermName(),diagnosis);
            }
            /*else{ //null
                DiagnosisDto diagnosisNew = new DiagnosisDto();
                diagnosisNew.setTermName(disease);
                diagnosisNew.setPositiveInspect(inspect.get检查());
                diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
            }*/
        }
    }

    private static void differentialDiagnosisProcess(String jsonStr) {
        DifferentialDiagnosisEntity differentialDiagnosis = JSON.parseObject(jsonStr,  DifferentialDiagnosisEntity.class);
        if(differentialDiagnosis.get鉴别诊断()!=null && !differentialDiagnosis.get鉴别诊断().isEmpty()){
            String disease = differentialDiagnosis.getName().toLowerCase();
            DiagnosisDto diagnosis= diagnosisMap.get(disease);
            if(diagnosis!=null){ //不为空
                diagnosis.setDifferentialDiagnosis(differentialDiagnosis.get鉴别诊断());
                diagnosisMap.put(diagnosis.getTermName(),diagnosis);
            }
            /*else{ //null
                DiagnosisDto diagnosisNew = new DiagnosisDto();
                diagnosisNew.setTermName(disease);
                diagnosisNew.setDifferentialDiagnosis(differentialDiagnosis.get鉴别诊断());
                diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
            }*/
        }
    }

    private static void diagnosticProcess(String jsonStr) {
        //System.out.println(jsonStr);
        DiagnosticFactorsEntity diagnosic = JSON.parseObject(jsonStr,  DiagnosticFactorsEntity.class);
        String disease = diagnosic.getDisease();
        if(diagnosic.getDisease().indexOf(":")>0){
            disease = diagnosic.getDisease().substring(0,diagnosic.getDisease().indexOf(":"));
        }
        DiagnosisDto diagnosis= diagnosisMap.get(disease);
        if(diagnosis!=null) { //不为空
            diagnosis.setPositiveSymptoms(diagnosic.getFactor());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }else{ //null
            DiagnosisDto diagnosisNew = new DiagnosisDto();
            diagnosisNew.setTermName(disease);
            diagnosisNew.setPositiveSymptoms(diagnosic.getFactor());
            diagnosisMap.put(diagnosisNew.getTermName(),diagnosisNew);
        }
    }
    private static void operationProcess(String jsonStr) {
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
