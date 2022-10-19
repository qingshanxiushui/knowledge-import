package com.knowledge.util;

import com.alibaba.fastjson.JSON;
import com.knowledge.dto.DiagnosisDto;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.DifferentialDiagnosisResultDto;
import com.knowledge.dto.PositiveSymptomReusltDto;
import com.knowledge.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BmjDataProcess {

    //诊断因素
    public static void diagnosticProcess(String jsonStr, HashMap<String, DiagnosisDto> diagnosisMap) {
        DiagnosticFactorsEntity diagnosic = JSON.parseObject(jsonStr,  DiagnosticFactorsEntity.class);
        String disease = diagnosic.getDisease();
        if(diagnosic.getDisease().indexOf(":")>0){
            disease = diagnosic.getDisease().substring(0,diagnosic.getDisease().indexOf(":"));
        }
        DiagnosisDto diagnosis = diagnosisMap.get(disease);
        if(diagnosis == null){
            diagnosis = new DiagnosisDto();
            diagnosis.setTermName(disease);
        }
        diagnosis.setPositiveSymptoms(diagnosic.getFactor());
        diagnosisMap.put(diagnosis.getTermName(),diagnosis);
    }

    //手术
    public static void operationProcess(String jsonStr, HashMap<String, DiagnosisDto> diagnosisMap) {
        OperationEntity operation = JSON.parseObject(jsonStr,  OperationEntity.class);
        DiagnosisDto diagnosis= diagnosisMap.get(operation.getDisease());
        if(diagnosis==null) {
            diagnosis = new DiagnosisDto();
            diagnosis.setTermName(operation.getDisease());
        }
        diagnosis.setOperationsName(operation.getOperations());
        diagnosisMap.put(diagnosis.getTermName(),diagnosis);
    }

    //量表
    public static void scaleProcess(String jsonStr, HashMap<String, DiagnosisDto> diagnosisMap) {
        ScaleEntity scale = JSON.parseObject(jsonStr,  ScaleEntity.class);
        DiagnosisDto diagnosis= diagnosisMap.get(scale.getDisease());
        if(diagnosis==null) {
            diagnosis = new DiagnosisDto();
            diagnosis.setTermName(scale.getDisease());
        }
        diagnosis.setScaleContent(scale.getList());
        diagnosisMap.put(diagnosis.getTermName(),diagnosis);
    }

    //鉴别诊断
    public static void differentialDiagnosisProcess(String jsonStr, HashMap<String, DiagnosisDto> diagnosisMap) {
        DifferentialDiagnosisEntity differentialDiagnosis = JSON.parseObject(jsonStr,  DifferentialDiagnosisEntity.class);
        if(differentialDiagnosis.get鉴别诊断()!=null && !differentialDiagnosis.get鉴别诊断().isEmpty()){
            String disease = differentialDiagnosis.getName().toLowerCase();
            DiagnosisDto diagnosis= diagnosisMap.get(disease);
            if(diagnosis==null){
                /*diagnosis = new DiagnosisDto();
                diagnosis.setTermName(disease);*/
                return;
            }
            diagnosis.setDifferentialDiagnosis(differentialDiagnosis.get鉴别诊断());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }
    }

    public static void positiveInspectProcess(String jsonStr, HashMap<String, DiagnosisDto> diagnosisMap) {
        InspectEntity inspect = JSON.parseObject(jsonStr,  InspectEntity.class);
        if(inspect.get检查()!=null && !inspect.get检查().isEmpty()){
            String disease = inspect.getName().toLowerCase();
            DiagnosisDto diagnosis= diagnosisMap.get(disease);
            if(diagnosis==null){
                /*diagnosis = new DiagnosisDto();
                diagnosis.setTermName(disease);*/
                return;
            }
            diagnosis.setPositiveInspect(inspect.get检查());
            diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        }
    }

    public static void diagnosisFlatMap(DiagnosisDto diagnosis,List<DiagnosisResultDto> diagnosisResultList, int serialNoExcel) {
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
            /*diagnosisResultDto.setTempFileType("BMJ");
            diagnosisResultDto.setTempTermName(diagnosis.getTermName());
            diagnosisResultDto.setTempSerialNo(serialNoExcel);*/
            diagnosisResultList.add(diagnosisResultDto);
        }
    }

}
