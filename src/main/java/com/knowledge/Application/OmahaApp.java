package com.knowledge.Application;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.knowledge.dto.DiagnosisDto;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.OmahaDto;
import com.knowledge.entity.OmahaEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OmahaApp {
    public static HashMap<String, OmahaDto> diagnosisMap = new HashMap<String, OmahaDto>();
    public static List<DiagnosisResultDto> diagnosisResultList = new ArrayList<DiagnosisResultDto>();
    public static int serialNoExcel = 0;
    public static void main( String[] args ) throws IOException {


        //读取文档获取数据
        List<OmahaEntity> omahaEntitySubList = readLocalFile();
        //List<OmahaEntity> omahaEntitySubList = mockData();

        omahaProcess(omahaEntitySubList);
        //printDiagnosisMap();

        System.out.println(diagnosisMap.size());
        for(String key:diagnosisMap.keySet()){
            omahaFlatMap(diagnosisMap.get(key));
        }

        System.out.println(diagnosisResultList.size());

        /*Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("诊断","临床表现"),
                DiagnosisResultDto.class, diagnosisResultList.subList(0,50000));
        FileOutputStream fos = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\omaha-export-a.xls");
        workbook.write(fos);
        fos.close();*/

        /*Workbook workbookA = ExcelExportUtil.exportExcel(new ExportParams("诊断","临床表现"),
                DiagnosisResultDto.class, diagnosisResultList.subList(49995,diagnosisResultList.size()));
        FileOutputStream fosA = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\omaha-export-b.xls");
        workbookA.write(fosA);
        fosA.close();*/

        Workbook workbookBig = ExcelExportUtil.exportBigExcel(
                new ExportParams("诊断","临床表现", ExcelType.XSSF),
                DiagnosisResultDto.class,

                );

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
                serialNoExcel = serialNoExcel+1;
                diagnosisResultDto.setTermName(omahaDto.getDiease());
                diagnosisResultDto.setSerialNo(String.valueOf(serialNoExcel));
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
            diagnosisResultList.add(diagnosisResultDto);
        }

    }

    private static void omahaProcess(List<OmahaEntity> omahaEntitySubList) {
        for (OmahaEntity omahaEntity: omahaEntitySubList) {
            String disease = omahaEntity.getEntity();
            OmahaDto omahaDto = diagnosisMap.get(disease);
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
            diagnosisMap.put(disease,omahaDto);
        }
    }

    private static List<OmahaEntity> mockData() {
        List<OmahaEntity> omahaEntitySubList = new ArrayList<OmahaEntity>();
        OmahaEntity omahaEntityA = new OmahaEntity("1","新型冠状病毒肺炎","疾病","相关检查","1","胸部影像学检查","观测操作","1","新型冠状病毒肺炎诊疗方案(试行第九版)");
        OmahaEntity omahaEntityB = new OmahaEntity("2","新型冠状病毒肺炎","疾病","临床表现","2","肺间质改变","临床所见","1","新型冠状病毒肺炎诊疗方案(试行第九版)");
        OmahaEntity omahaEntityC = new OmahaEntity("3","小脑扁桃体下疝畸形","疾病","临床表现","3","尿便障碍","疾病","3","神经病学(第8版)");
        omahaEntitySubList.add(omahaEntityA);
        omahaEntitySubList.add(omahaEntityB);
        omahaEntitySubList.add(omahaEntityC);
        return omahaEntitySubList;
    }

    private static void printDiagnosisMap() {
        System.out.println(diagnosisMap.size());
        int i=0;
        for(Map.Entry<String,OmahaDto> entry :diagnosisMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
            i++;
            if(i>=3){
                break;
            }
        }
    }
    private static List<OmahaEntity> readLocalFile() {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<OmahaEntity> omahaEntityALLList = ExcelImportUtil.importExcel(new File("E:\\医疗\\知识导入\\知识导入任务\\OMAHA\\汇知医学知识图谱_疾病_20220820.xlsx"),OmahaEntity.class, params);
        System.out.println(omahaEntityALLList.size());
        List<String> propertyList = new ArrayList<String>();
        propertyList.add("与…鉴别诊断");
        propertyList.add("临床表现");
        propertyList.add("症状");
        propertyList.add("体征");
        propertyList.add("相关检查");
        propertyList.add("实验室检查");
        propertyList.add("诊断相关检查");
        propertyList.add("治疗相关检查");
        List<OmahaEntity> omahaEntitySubList = omahaEntityALLList.stream().filter(e->e.getEntityTag().equals("疾病")).filter(e->propertyList.contains(e.getProperty())).collect(Collectors.toList());
        System.out.println(omahaEntitySubList.size());
        return omahaEntitySubList;
    }
}
