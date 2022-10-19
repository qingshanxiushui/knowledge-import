package com.knowledge.Application;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.alibaba.fastjson.JSON;
import com.knowledge.dto.*;
import com.knowledge.entity.*;
import com.knowledge.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class MergeApp {
    public static HashMap<String, OmahaDto> omahaDtoHashMap = new HashMap<String, OmahaDto>();

    public static HashMap<String, DiagnosisDto> diagnosisMap = new HashMap<String,DiagnosisDto>();

    public static int mergerSerialNoExcel = 0;

    public static List<DiagnosisResultDto> mergerDiagnosisResultList = new ArrayList<DiagnosisResultDto>();

    public static int isInner = 1;

    public static void main( String[] args ) throws IOException {

        List<OmahaEntity> omahaEntitySubList = readLocalFileDirectSub();
        omahaProcess(omahaEntitySubList);

        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素","诊断因素");
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术","手术");
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-量表","量表");
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-鉴别诊断","鉴别诊断");
        fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-检查","检查");

        System.out.println(omahaDtoHashMap.size());
        System.out.println(diagnosisMap.size());

        isInner = 1;
        for(String key: omahaDtoHashMap.keySet()){
            omahaFlatMap(omahaDtoHashMap.get(key));
            if(diagnosisMap.get(key)!=null){
                diagnosisFlatMap(diagnosisMap.get(key));
                diagnosisMap.remove(key);
            }
        }
        isInner = 0;
        for(String key:diagnosisMap.keySet()){
            diagnosisFlatMap(diagnosisMap.get(key));
        }

        System.out.println(mergerDiagnosisResultList.size());

        exportLocalExcel();
    }

    private static List<OmahaEntity> readLocalFileDirectSub() {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<OmahaEntity> omahaEntitySubList = ExcelImportUtil.importExcel(new File("E:\\医疗\\知识导入\\知识导入任务\\OMAHA\\汇知医学知识图谱_疾病_20220820-sub.xlsx"),OmahaEntity.class, params);
        System.out.println(omahaEntitySubList.size());
        return omahaEntitySubList;
    }

    private static void omahaProcess(List<OmahaEntity> omahaEntitySubList) {
        for (OmahaEntity omahaEntity: omahaEntitySubList) {
            String disease = omahaEntity.getEntity();
            OmahaDto omahaDto = omahaDtoHashMap.get(disease);
            if(omahaDto == null){
                omahaDto = new OmahaDto();
                omahaDto.setDiease(disease);
            }
            switch(omahaEntity.getProperty()) {
                case "与…鉴别诊断":
                    omahaDto.getIdentifyList().add(omahaEntity); //诊断
                    break;
                case "临床表现":
                case "症状":
                    omahaDto.getPositiveSymptomList().add(omahaEntity); //症状
                    break;
                case "体征":
                    omahaDto.getPositiveSignAList().add(omahaEntity); //体征
                    break;
                case "实验室检查":
                    omahaDto.getPositiveExamineAList().add(omahaEntity); //检验
                    break;
                case "相关检查":
                case "诊断相关检查":
                case "治疗相关检查":
                    omahaDto.getPositiveInspectAList().add(omahaEntity); //检查
                    break;
                default:
                    System.out.println("default");
            }
            omahaDtoHashMap.put(disease,omahaDto);
        }
    }

    private static void omahaFlatMap(OmahaDto omahaDto) {
        int identifyListSize = omahaDto.getIdentifyList().isEmpty()?0:omahaDto.getIdentifyList().size();
        int positiveSymptomListSize = omahaDto.getPositiveSymptomList().isEmpty()?0:omahaDto.getPositiveSymptomList().size();
        int positiveSignAListSize = omahaDto.getPositiveSignAList().isEmpty()?0:omahaDto.getPositiveSignAList().size();
        int positiveInspectAListSize = omahaDto.getPositiveInspectAList().isEmpty()?0:omahaDto.getPositiveInspectAList().size();
        int positiveExamineAListSize = omahaDto.getPositiveExamineAList().isEmpty()?0:omahaDto.getPositiveExamineAList().size();
        int maxSize = Math.max(Math.max(Math.max(Math.max(identifyListSize,positiveSymptomListSize),positiveSignAListSize),positiveInspectAListSize),positiveExamineAListSize);
        int serialNo = 0;
        for(serialNo =0;serialNo < maxSize; serialNo++){
            DiagnosisResultDto diagnosisResultDto = new DiagnosisResultDto();
            if(serialNo==0){  //首记录设置序号1
                mergerSerialNoExcel = mergerSerialNoExcel +1;
                diagnosisResultDto.setTermName(omahaDto.getDiease());
                diagnosisResultDto.setSerialNo(String.valueOf(mergerSerialNoExcel));
            }
            if(serialNo<identifyListSize){ //诊断
                diagnosisResultDto.setIdentifyDisease(omahaDto.getIdentifyList().get(serialNo).getValue());
                diagnosisResultDto.setIdentifySource(omahaDto.getIdentifyList().get(serialNo).getSource());
            }
            if(serialNo<positiveSymptomListSize){ //阳性症状
                diagnosisResultDto.setPositiveSymptomName(omahaDto.getPositiveSymptomList().get(serialNo).getValue());
                diagnosisResultDto.setPositiveSymptomSource(omahaDto.getPositiveSymptomList().get(serialNo).getSource());
            }
            if(serialNo<positiveSignAListSize){ //体征
                diagnosisResultDto.setPositiveSignAResult(omahaDto.getPositiveSignAList().get(serialNo).getValue());
                diagnosisResultDto.setPositiveSignASource(omahaDto.getPositiveSignAList().get(serialNo).getSource());
            }
            if(serialNo<positiveInspectAListSize){ //检查
                diagnosisResultDto.setPositiveInspectAName(omahaDto.getPositiveInspectAList().get(serialNo).getValue());
                diagnosisResultDto.setPositiveInspectASource(omahaDto.getPositiveInspectAList().get(serialNo).getSource());
            }
            if(serialNo<positiveExamineAListSize){ //检验
                diagnosisResultDto.setPositiveExamineAName(omahaDto.getPositiveExamineAList().get(serialNo).getValue());
                diagnosisResultDto.setPositiveExamineASource(omahaDto.getPositiveExamineAList().get(serialNo).getSource());
            }
            mergerDiagnosisResultList.add(diagnosisResultDto);
        }

    }

    private static void exportLocalExcel() throws IOException {
        int totalPage = (mergerDiagnosisResultList.size() / 10000) + 1;
        int pageSize = 10000;
        Workbook workbookBig = ExcelExportUtil.exportBigExcel(
                new ExportParams("诊断","临床表现", ExcelType.XSSF),
                DiagnosisResultDto.class,
                new IExcelExportServer(){

                    /**
                     * queryParams 就是下面的totalPage，限制条件
                     * page 是页数，他是在分页进行文件转换，page每次+1
                     */
                    @Override
                    public List<Object> selectListForExcelExport(Object queryParams, int page) {
                        //很重要！！这里面整个方法体，其实就是将所有的数据aList分批返回处理
                        //分批的方式很多，我直接用了subList。然后 每批不能太大。我试了30000一批，
                        //内存溢出，貌似 最大每批10000
                        if (page > totalPage) {
                            return null;
                        }
                        // fromIndex开始索引，toIndex结束索引
                        int fromIndex = (page - 1) * pageSize;
                        int toIndex = page != totalPage ? fromIndex + pageSize : mergerDiagnosisResultList.size();
                        List<Object> list = new ArrayList<>();
                        list.addAll(mergerDiagnosisResultList.subList(fromIndex, toIndex));
                        return list;
                    }
                },
                totalPage
        );
        FileOutputStream fosBig = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\merger-export.xlsx");
        workbookBig.write(fosBig);
        fosBig.close();
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
            if(serialNo==0 && isInner ==0 ){  //首记录设置序号1
                mergerSerialNoExcel = mergerSerialNoExcel+1;
                diagnosisResultDto.setTermName(diagnosis.getTermName());
                diagnosisResultDto.setSerialNo(String.valueOf(mergerSerialNoExcel));
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
            mergerDiagnosisResultList.add(diagnosisResultDto);
        }
    }
}
