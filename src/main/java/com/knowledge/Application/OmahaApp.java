package com.knowledge.Application;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.OmahaDto;
import com.knowledge.entity.OmahaEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OmahaApp {
    public static HashMap<String, OmahaDto> omahaDtoHashMap = new HashMap<String, OmahaDto>();
    public static List<DiagnosisResultDto> omahaDiagnosisResultList = new ArrayList<DiagnosisResultDto>();
    public static int omahaSerialNoExcel = 0;
    public static void main( String[] args ) throws IOException {


        //读取文档获取数据
        //List<OmahaEntity> omahaEntitySubList = readLocalFile();
        List<OmahaEntity> omahaEntitySubList = readLocalFileDirectSub();
        //List<OmahaEntity> omahaEntitySubList = mockData();

        omahaProcess(omahaEntitySubList);
        //printDiagnosisMap();

        System.out.println(omahaDtoHashMap.size());
        for(String key: omahaDtoHashMap.keySet()){
            omahaFlatMap(omahaDtoHashMap.get(key));
        }

        System.out.println(omahaDiagnosisResultList.size());

        exportLocalExcel();
    }

    private static void exportLocalExcel() throws IOException {
        int totalPage = (omahaDiagnosisResultList.size() / 10000) + 1;
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
                        int toIndex = page != totalPage ? fromIndex + pageSize : omahaDiagnosisResultList.size();
                        List<Object> list = new ArrayList<>();
                        list.addAll(omahaDiagnosisResultList.subList(fromIndex, toIndex));
                        return list;
                    }
                },
                totalPage
                );
        FileOutputStream fosBig = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\omaha-export.xlsx");
        workbookBig.write(fosBig);
        fosBig.close();
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
                omahaSerialNoExcel = omahaSerialNoExcel +1;
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
        System.out.println(omahaDtoHashMap.size());
        int i=0;
        for(Map.Entry<String,OmahaDto> entry : omahaDtoHashMap.entrySet()){
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

    private static List<OmahaEntity> readLocalFileDirectSub() {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<OmahaEntity> omahaEntitySubList = ExcelImportUtil.importExcel(new File("E:\\医疗\\知识导入\\知识导入任务\\OMAHA\\汇知医学知识图谱_疾病_20220820-sub.xlsx"),OmahaEntity.class, params);
        System.out.println(omahaEntitySubList.size());
        return omahaEntitySubList;
    }


}
