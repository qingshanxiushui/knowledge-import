package com.knowledge.util;

import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.OmahaDto;
import com.knowledge.entity.OmahaEntity;

import java.util.HashMap;
import java.util.List;

public class OmahaDataProcess {
    public static void omahaProcess(List<OmahaEntity> omahaEntitySubList, HashMap<String, OmahaDto> omahaDtoHashMap) {
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

    public static void omahaFlatMap(OmahaDto omahaDto,List<DiagnosisResultDto> omahaDiagnosisResultList,int omahaSerialNoExcel) {
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
                diagnosisResultDto.setTermName(omahaDto.getDiease());
                diagnosisResultDto.setSerialNo(String.valueOf(omahaSerialNoExcel));
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
            omahaDiagnosisResultList.add(diagnosisResultDto);
        }

    }
}
