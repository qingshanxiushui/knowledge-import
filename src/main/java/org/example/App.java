package org.example;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.knowledge.dto.DiagnosisDto;
import com.knowledge.entity.*;
import com.knowledge.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException {
        //System.out.println("Wiskott-Aldrich综合征".toLowerCase()+";"+"Addison病".toLowerCase());
        //System.out.println(Math.max(8,Math.max(7,6)));
        //importLocalExcel();
        //exportLocalExcel();
        //diagnosisMapProcess();
        //mapOperation();
        //fileList();
        //fileToObject();
    }

    private static void importLocalExcel() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<UserEntity> UserEntityList = ExcelImportUtil.importExcel(new File("E:\\医疗\\知识导入\\知识导入任务\\easypoi-user.xls"),UserEntity.class, params);
        System.out.println(UserEntityList.toString());
    }

    private static void exportLocalExcel() throws IOException {
        List<UserEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName("张三" + i);
            userEntity.setAge(20 + i);
            userEntity.setTime(new Date(System.currentTimeMillis() + i));
            dataList.add(userEntity);
        }
        //生成excel文档
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户","用户信息"),
                UserEntity.class, dataList);
        FileOutputStream fos = new FileOutputStream("E:\\医疗\\知识导入\\知识导入任务\\easypoi-user.xls");
        workbook.write(fos);
        fos.close();
    }

    private static void diagnosisMapProcess() {
        HashMap<String,DiagnosisDto> diagnosisMap = new HashMap<String,DiagnosisDto>();
        System.out.println("---------手术--------------");
        DiagnosisDto diagnosis = new DiagnosisDto();
        String operationFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术\\lambert-eaton肌无力综合征.json");
        OperationEntity operation = JSON.parseObject(operationFile,  OperationEntity.class);
        System.out.println(operation.toString());
        diagnosis.setTermName(operation.getDisease());
        diagnosis.setOperationsName(operation.getOperations());
        diagnosisMap.put(diagnosis.getTermName(),diagnosis);
        System.out.println(diagnosisMap);
        System.out.println("---------诊断因素--------------");
        String diagnosicFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素\\lambert-eaton肌无力综合征\\lambert-eaton肌无力综合征.txt");
        DiagnosticFactorsEntity diagnosic = JSON.parseObject(diagnosicFile,  DiagnosticFactorsEntity.class);
        System.out.println(diagnosic.toString());
        DiagnosisDto diagnosis2= diagnosisMap.get(diagnosic.getDisease());
        diagnosis2.setPositiveSymptoms(diagnosic.getFactor());
        System.out.println(diagnosis2.toString());
        diagnosisMap.put(diagnosis2.getTermName(),diagnosis2);
        System.out.println("---------map测试--------------");
        DiagnosisDto diagnosis3= diagnosisMap.get("test");
        if(diagnosis3!=null) {
            System.out.println(diagnosis3.toString());
        }else{
            System.out.println("null数据");
            diagnosisMap.put("test",null);
        }
        System.out.println(diagnosisMap.size());
    }

    private static void mapOperation() {
        HashMap<String,String> resultMidMap = new HashMap<String,String>();
        resultMidMap.put("1","a");
        resultMidMap.put("2","b");
        resultMidMap.put("3","c");
        System.out.println(resultMidMap);
        resultMidMap.remove("1");
        System.out.println(resultMidMap.get(1));
        System.out.println(resultMidMap.get(1)==null);
        System.out.println(resultMidMap);
        resultMidMap.put("2","bb");
        System.out.println(resultMidMap);
        for(Map.Entry<String,String> entry :resultMidMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }
    }

    private static void fileList() {
        ArrayList<File> fileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-量表\\",fileList);
        System.out.println(fileList.size());
        System.out.println(fileList);
    }

    public static void fileToObject(){

        //String operationFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术\\barrett食管.json");
        //OperationEntity operation = JSON.parseObject(operationFile,  OperationEntity.class);

        //读取手术文件
        /*String operationFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术\\barrett食管.json");
        System.out.println(operationFile);
        JSONObject operationObj=JSON.parseObject(operationFile);
        System.out.println("输出disease："+operationObj.getString("disease"));
        System.out.println("输出operations："+operationObj.getJSONArray("operations").getString(1));
        OperationEntity operation = JSON.parseObject(operationFile,  OperationEntity.class);
        System.out.println(operation.toString());
        System.out.println(operation.getDisease());
        System.out.println(operation.getOperations().get(1));
        System.out.println( "----------------读取手术文件结束----------------" );*/

        //读取量表文件
        /*String scaleFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-量表\\社会焦虑障碍.json");
        System.out.println(scaleFile);
        JSONObject scaleFileObj=JSON.parseObject(scaleFile);
        System.out.println("输出disease："+scaleFileObj.getString("disease"));
        System.out.println("输出operations："+scaleFileObj.getJSONArray("list").get(1));
         System.out.println( "----------------读取量表文件开始----------------" );
        ScaleEntity scale = JSON.parseObject(scaleFile,  ScaleEntity.class);
        System.out.println(scale.toString());
        System.out.println(scale.getDisease());
        System.out.println(scale.getList().get(1));
        System.out.println(scale.getList().get(1).getName());
        System.out.println(scale.getList().get(1).getContent());
        System.out.println( "----------------读取量表文件结束----------------" );*/

        //读取诊断因素文件
        //String diagnosisFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素\\肺栓塞\\肺栓塞.txt");
        /*String diagnosisFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素\\（血液）高凝状态\\（血液）高凝状态.txt");
        System.out.println(diagnosisFile);
        JSONObject diagnosisObj=JSON.parseObject(diagnosisFile);
        System.out.println("输出disease："+diagnosisObj.getString("disease"));
        System.out.println("输出operations："+diagnosisObj.getJSONArray("symptom_annotate_list").get(1));
        System.out.println("输出operations："+diagnosisObj.getJSONArray("factor").get(1));
        System.out.println( "----------------读取诊断因素文件开始----------------" );
        DiagnosticFactorsEntity diagnosis = JSON.parseObject(diagnosisFile,  DiagnosticFactorsEntity.class);
        System.out.println(diagnosis.toString());
        System.out.println(diagnosis.getDisease());
        String disease = diagnosis.getDisease();
        if(diagnosis.getDisease().indexOf(":")>0){
            disease = diagnosis.getDisease().substring(0,diagnosis.getDisease().indexOf(":"));
        }
        System.out.println("疾病截取后："+disease);
        System.out.println(diagnosis.getSymptom_annotate_list().get(1));
        System.out.println(diagnosis.getFactor().get(1));
        System.out.println(diagnosis.getFactor().get(1).getDesc());
        System.out.println( "----------------读取诊断因素文件结束----------------" );*/

        //读取鉴别诊断文件
        /*String differentialDiagnosisFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-鉴别诊断\\Addison病.txt");
        System.out.println( "----------------读取鉴别诊断文件开始----------------" );
        DifferentialDiagnosisEntity differentialDiagnosis = JSON.parseObject(differentialDiagnosisFile,  DifferentialDiagnosisEntity.class);
        System.out.println(differentialDiagnosis.toString());
        System.out.println(differentialDiagnosis.getName());
        System.out.println(differentialDiagnosis.get鉴别诊断().get(1).get疾病());
        System.out.println(differentialDiagnosis.get鉴别诊断().get(0).get症状().get(1));
        System.out.println( "----------------读取鉴别诊断文件结束----------------" );*/

        //读取检查文件
        /*String inspectFile = FileUtil.readJsonFile("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-检查\\Addison病.txt");
        System.out.println( "----------------读取检查文件开始----------------" );
        InspectEntity inspect = JSON.parseObject(inspectFile,  InspectEntity.class);
        System.out.println(inspect.toString());
        System.out.println(inspect.getName());
        System.out.println(inspect.getName().toLowerCase());
        System.out.println(inspect.get检查().get(0).getItems().get(0));
        System.out.println(inspect.get检查().get(1).getItems().get(1));
        System.out.println(inspect.get检查().stream().flatMap(inspectSubEntity -> inspectSubEntity.getItems().stream()).collect(Collectors.toList()).get(5));
        System.out.println( "----------------读取检查文件结束----------------" );*/

    }
}
