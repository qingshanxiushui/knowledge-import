package com.study.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.deepcogni.mrqcs.regex.MatchItem;
import com.study.http.dto.*;
import com.study.postgre.postgreTest;
import com.study.regexp.TemplateParamgraphEngine;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.util.*;


public class ColumnToJson {
    public static void main(String[] args) throws MyException, IOException {
        //System.out.println(MockDataUtil.getObjectJson());

        //jsonToMap();
        //System.out.println(columnToJsonObject());
        //System.out.println(columnToJsonDefine());
        //System.out.println(columnToJsonObjectTwo());
        //System.out.println(columnToJsonObjectThree());
        //System.out.println(columnToJsonObjectFour());
        //System.out.println(columnToJsonObjectFiveDbOne());
        //System.out.println(columnToJsonObjectFiveDbTwo());
        //System.out.println(columnToJsonObjectSixRegExpOne());
        System.out.println(columnToJsonObjectSixRegExpTwo());

    }

    /**
     * 输入 列值、数据库json结构,入院记录解析。需多text进行正则拆解。
     * 利用 数据库json结构 构造json
     * @return
     */
    private static String columnToJsonObjectSixRegExpTwo() throws IOException {
        List<String> columnListOne= MockDataUtil.getInpatientColumn(); //获取列值
        List<String> columnListTwo= MockDataUtil.getInpatientColumnTwo(); //获取列值
        List<List> columnListList = new ArrayList<List>();
        columnListList.add(columnListOne);
        columnListList.add(columnListTwo);
        JSONArray emrContext = postgreTest.accessPostgre();//获取db json结构
        //处理emr信息
        JSONArray emrJsonArray = new JSONArray();
        JSONObject emrJsonObject;
        for(int columnIndex =0;columnIndex<columnListList.size();columnIndex++){
            emrJsonObject = getJsonObjectRegExp(columnListList.get(columnIndex), emrContext);
            emrJsonArray.add(emrJsonObject);
        }
        //处理头信息
        JSONObject httpJson = new JSONObject();
        httpJson.put("action","save_emr");
        JSONArray typeJson = new JSONArray();
        httpJson.put("type",typeJson);
        JSONArray paramsJson = new JSONArray();
        httpJson.put("params",paramsJson);
        httpJson.put("emr_contents",emrJsonArray);
        return httpJson.toJSONString();
    }
    /**
     * 输入 列值、数据库json结构,入院记录解析。需多text进行正则拆解。
     * 利用 数据库json结构 构造json
     * @return
     */
    private static String columnToJsonObjectSixRegExpOne() throws IOException {
        List<String> columnList= MockDataUtil.getInpatientColumn(); //获取列值
        JSONArray emrContext = postgreTest.accessPostgre();//获取db json结构
        //处理emr信息
        JSONArray emrJsonArray = new JSONArray();
        JSONObject emrJsonObject = getJsonObjectRegExp(columnList, emrContext);
        emrJsonArray.add(emrJsonObject);
        //处理头信息
        JSONObject httpJson = new JSONObject();
        httpJson.put("action","save_emr");
        JSONArray typeJson = new JSONArray();
        httpJson.put("type",typeJson);
        JSONArray paramsJson = new JSONArray();
        httpJson.put("params",paramsJson);
        httpJson.put("emr_contents",emrJsonArray);
        return httpJson.toJSONString();
    }

    private static JSONObject getJsonObjectRegExp(List<String> columnList, JSONArray emrContext) {
        int columnIndex = 0;
        int columnSize = columnList.size()-1;
        Map<String,Object> textMap = parseText(columnList.get(columnSize));

        JSONObject emrJsonObject = new JSONObject();
        for (Object emrObject : emrContext){
            JSONObject emr = (JSONObject)emrObject;
            if(((String)emr.get("data_type")).equals("object")){
                JSONObject emrPatientObject = new JSONObject();
                for (Object patientObject : (JSONArray)emr.get("item")){
                    JSONObject patient = (JSONObject)patientObject;
                    emrPatientObject.put((String)patient.get("field_name"), columnIndex<columnSize?columnList.get(columnIndex):((textMap.get((String)emr.get("name")))==null?"":textMap.get((String)emr.get("name"))));
                    columnIndex = columnIndex + 1;
                }
                emrJsonObject.put((String)emr.get("field_name"),emrPatientObject);
            }else if(((String)emr.get("data_type")).equals("array")){
                JSONArray diagnosisJsonArray = new JSONArray();
                JSONObject diagnosisJsonObject;
                if(((String)emr.get("name")).equals("诊断")){
                    List<String> diagnosisList = (ArrayList<String>)textMap.get((String)emr.get("name"));
                    for(String diagnosis:diagnosisList){
                        String diagnosis_type = diagnosis.split(":")[0];
                        String diagnosis_names = diagnosis.split(":")[1].trim();
                        for(String diagnosis_name:diagnosis_names.split("\n|\t")){
                            diagnosisJsonObject = new JSONObject();
                            diagnosisJsonObject.put("type", diagnosis_type);
                            diagnosisJsonObject.put("name", diagnosis_name);
                            diagnosisJsonObject.put("is_principal", "T");
                            diagnosisJsonArray.add(diagnosisJsonObject);
                        }
                    }
                }
                emrJsonObject.put((String)emr.get("field_name"),diagnosisJsonArray);
            }else{

                emrJsonObject.put((String)emr.get("field_name"), columnIndex<columnSize?columnList.get(columnIndex):((textMap.get((String)emr.get("name")))==null?"":textMap.get((String)emr.get("name"))));
                columnIndex = columnIndex + 1;
            }
        }
        return emrJsonObject;
    }

    private static Map<String,Object> parseText(String text){
        Map<String,Object> textMap = new HashMap<>();
        List<String> diagnosis = new ArrayList<String>();
        List<MatchItem> matchItems = TemplateParamgraphEngine.getParagraphEngine("1").parse(text);
        matchItems.sort(new Comparator<MatchItem>() {
            public int compare(MatchItem o1, MatchItem o2) {
                return o1.getOffset() - o2.getOffset();
            }
        });
        String paragraph = StringUtils.EMPTY;
        for (int loop =0; loop<matchItems.size();loop++) {
            MatchItem item = matchItems.get(loop);
            //System.out.println("MatchItem[ type="+item.getType()+",offset="+item.getOffset()+",word="+item.getWord()+"]");
            String type = item.getType();
            int offset = item.getOffset();
            String content = item.getWord();
            // 截取相邻段落名称之间的文本作为段落内容
            paragraph = text.substring(offset + content.length(),(loop+1)<matchItems.size()?matchItems.get(loop+1).getOffset():text.length());
            //System.out.println("paragraph="+paragraph);
            if(type.contains("诊断")){
                diagnosis.add(type+":"+paragraph);
            }
            textMap.put(type,paragraph);
        }
        textMap.put("诊断",diagnosis);
        //System.out.println(textMap.toString());
        return textMap;
    }

    /**
     * 输入 列值(多条记录)、数据库json结构,
     * 利用 数据库json结构 构造json
     * @return
     */
    private static String columnToJsonObjectFiveDbTwo() throws IOException {
        List<String> columnList= MockDataUtil.getDbColumn(); //获取列值
        List<String> columnListTwo= MockDataUtil.getDbColumnTwo(); //获取列值
        List<List> columnListList = new ArrayList<List>();
        columnListList.add(columnList);
        columnListList.add(columnListTwo);
        JSONArray emrContext = postgreTest.accessPostgre();//获取db json结构
        //处理emr信息
        JSONArray emrJsonArray = new JSONArray();
        JSONObject emrJsonObject;
        for(int columnIndex =0;columnIndex<columnListList.size();columnIndex++){
            emrJsonObject = getJsonObject(columnListList.get(columnIndex), emrContext);
            emrJsonArray.add(emrJsonObject);
        }
        //处理头信息
        JSONObject httpJson = new JSONObject();
        httpJson.put("action","save_emr");
        JSONArray typeJson = new JSONArray();
        httpJson.put("type",typeJson);
        JSONArray paramsJson = new JSONArray();
        httpJson.put("params",paramsJson);
        httpJson.put("emr_contents",emrJsonArray);
        return httpJson.toJSONString();
    }

    /**
     * 输入 列值(单条记录)、数据库json结构,
     * 利用 数据库json结构 构造json
     * @return
     */
    public static String columnToJsonObjectFiveDbOne() throws IOException {
        List<String> columnList= MockDataUtil.getDbColumn(); //获取列值
        JSONArray emrContext = postgreTest.accessPostgre();//获取db json结构
        //处理emr信息
        JSONArray emrJsonArray = new JSONArray();
        JSONObject emrJsonObject = getJsonObject(columnList, emrContext);
        emrJsonArray.add(emrJsonObject);
        //处理头信息
        JSONObject httpJson = new JSONObject();
        httpJson.put("action","save_emr");
        JSONArray typeJson = new JSONArray();
        httpJson.put("type",typeJson);
        JSONArray paramsJson = new JSONArray();
        httpJson.put("params",paramsJson);
        httpJson.put("emr_contents",emrJsonArray);
        return httpJson.toJSONString();
    }

    private static JSONObject getJsonObject(List<String> columnList, JSONArray emrContext) {
        int columnIndex = 0;
        JSONObject emrJsonObject = new JSONObject();
        for (Object emrObject : emrContext){
            JSONObject emr = (JSONObject)emrObject;
            if(((String)emr.get("data_type")).equals("object")){
                JSONObject emrPatientObject = new JSONObject();
                for (Object patientObject : (JSONArray)emr.get("item")){
                    JSONObject patient = (JSONObject)patientObject;
                    emrPatientObject.put((String)patient.get("field_name"), columnList.get(columnIndex));
                    columnIndex = columnIndex + 1;
                }
                emrJsonObject.put((String)emr.get("field_name"),emrPatientObject);
            }else if(((String)emr.get("data_type")).equals("array")){
                JSONArray resultJsonArray = new JSONArray();
                JSONObject resultJsonObject;
                for (Object resultObject : (JSONArray)emr.get("item")){
                    JSONObject result = (JSONObject)resultObject;
                    int resultLength = columnList.get(columnIndex).split(";").length;
                    for(int loop=0;loop<resultLength;loop++){
                        if(loop>= resultJsonArray.size()){
                            resultJsonObject = new JSONObject();
                            resultJsonArray.add(resultJsonObject);
                        }else{
                            resultJsonObject = (JSONObject)resultJsonArray.get(loop);
                        }
                        resultJsonObject.put((String)result.get("field_name"), columnList.get(columnIndex).split(";")[loop]);
                        resultJsonArray.set(loop,resultJsonObject);
                    }
                    columnIndex = columnIndex +1;
                }
                emrJsonObject.put((String)emr.get("field_name"),resultJsonArray);
            }else{
                emrJsonObject.put((String)emr.get("field_name"), columnList.get(columnIndex));
                columnIndex = columnIndex + 1;
            }
        }
        return emrJsonObject;
    }

    /**
     * 输入 列值、对应元素名(ermcontent是多条记录列表),
     * 利用 对象类 构造json
     * @return
     */
    public static String columnToJsonObjectFour(){
        //构造数据
        List<String> elementList= MockDataUtil.getElement().subList(4,MockDataUtil.getElement().size());
        List<String> columnListTwo= MockDataUtil.getColumnTwo();
        List<String> columnListThree= MockDataUtil.getColumnThree();
        List<List> columnListList = new ArrayList<List>();
        columnListList.add(columnListThree.subList(4,columnListTwo.size()));
        columnListList.add(columnListTwo.subList(4,columnListTwo.size()));
        //处理emrContent
        List emrContentList = new ArrayList<ClinicEmrDto>();
        for(List<String> columnList:columnListList){
            List emrContentListTmp = new ArrayList<ClinicEmrDto>();
            for(int i=0; i<elementList.size();i++){
                String element = elementList.get(i);
                emrContentListTmp = processEmrContent(columnList.get(i),emrContentListTmp,element);
            }
            emrContentList.addAll(emrContentListTmp);
        }
        //构造最终结果clinicDto
        ClinicDto clinicDto = new ClinicDto();
        clinicDto.setAction("save_emr");
        clinicDto.setEmr_contents(emrContentList);

        return JSON.toJSONString(clinicDto);
    }

    /**
     * 输入 列值、对应元素名(ermcontent是按分隔符链接多条记录),
     * 利用 对象类 构造json
     * @return
     */
    public static String columnToJsonObjectThree(){
        List<String> elementList= MockDataUtil.getElement();
        //List<String> columnList= MockDataUtil.getColumnThree();
        List<String> columnList= MockDataUtil.getColumnTwo();

        ClinicDto clinicDto = new ClinicDto();
        List<Object> paramList = new ArrayList<>();
        List emrContentList = new ArrayList<Object>();

        for (int i=0; i<elementList.size();i++){
            String element = elementList.get(i);
            switch (element){
                case "action":
                    clinicDto.setAction(columnList.get(i));
                    break;
                case "type":
                    List type = new ArrayList<>();
                    for(int typeLength=0;typeLength< columnList.get(i).split(";").length;typeLength++){
                        type.add(columnList.get(i).split(";")[typeLength]);
                    }
                    clinicDto.setType(type);
                    break;
                case "param_visit_id":
                    Map paramVisitMap = new HashMap();
                    paramVisitMap.put("name","visit_id");
                    paramVisitMap.put("value",columnList.get(i));
                    paramList.add(paramVisitMap);
                    break;
                case "param_patient_id":
                    Map paramPatientMap = new HashMap();
                    paramPatientMap.put("name","patient_id");
                    paramPatientMap.put("value",columnList.get(i));
                    paramList.add(paramPatientMap);
                    break;
                case "record_id":
                case "record_type":
                case "medical_id":
                case "visit_id":
                case "patient_info_id":
                case "patient_info_age":
                case "patient_info_gender":
                case "patient_info_birth_date":
                case "record_time":
                case "admission_time":
                case "hospital":
                case "dept":
                case "apply_dept":
                case "exam_name":
                case "exam_method":
                case "sample_category":
                case "sample_status":
                case "sample_id":
                case "sample_time":
                case "receive_time":
                case "exam_time":
                case "report_time":
                case "item_name":
                case "item_abbr":
                case "item_result":
                case "item_unit":
                case "item_hint":
                    emrContentList = processEmrContent(columnList.get(i),emrContentList,element);
                    break;
                default:
                    System.out.println("没有该值");
            }
        }
        clinicDto.setParams(paramList);
        clinicDto.setEmr_contents(emrContentList);
        return JSON.toJSONString(clinicDto);
    }

    private static List processEmrContent(String value, List emrContentList, String element){
        ClinicEmrDto clinicEmrDto;
        ClinicEmrPatientInfo clinicEmrPatientInfo;
        int resultInfoLength = value.split("&").length;
        for(int loop=0;loop<resultInfoLength;loop++){
            if(loop>= emrContentList.size()){
                clinicEmrDto = new ClinicEmrDto();
                emrContentList.add(clinicEmrDto);
            }else{
                clinicEmrDto = (ClinicEmrDto)emrContentList.get(loop);
            }
            switch (element){
                case "record_id":
                    clinicEmrDto.setRecord_id(value.split("&")[loop]);
                case "record_type":
                    clinicEmrDto.setRecord_type(value.split("&")[loop]);
                    break;
                case "medical_id":
                    clinicEmrDto.setMedical_id(value.split("&")[loop]);
                    break;
                case "visit_id":
                    clinicEmrDto.setVisit_id(value.split("&")[loop]);
                    break;
                case "patient_info_id":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)clinicEmrDto.getPatient_info();
                    if(clinicEmrPatientInfo == null){
                        clinicEmrPatientInfo =  new ClinicEmrPatientInfo();
                    }
                    clinicEmrPatientInfo.setId(value.split("&")[loop]);
                    clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
                    break;
                case "patient_info_age":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)clinicEmrDto.getPatient_info();
                    if(clinicEmrPatientInfo == null){
                        clinicEmrPatientInfo =  new ClinicEmrPatientInfo();
                    }
                    clinicEmrPatientInfo.setAge(value.split("&")[loop]);
                    clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
                    break;
                case "patient_info_gender":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)clinicEmrDto.getPatient_info();
                    if(clinicEmrPatientInfo == null){
                        clinicEmrPatientInfo =  new ClinicEmrPatientInfo();
                    }
                    clinicEmrPatientInfo.setGender(value.split("&")[loop]);
                    clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
                    break;
                case "patient_info_birth_date":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)clinicEmrDto.getPatient_info();
                    if(clinicEmrPatientInfo == null){
                        clinicEmrPatientInfo =  new ClinicEmrPatientInfo();
                    }
                    clinicEmrPatientInfo.setBirth_date(value.split("&")[loop]);
                    clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
                    break;
                case "record_time":
                    clinicEmrDto.setRecord_time(value.split("&")[loop]);
                    break;
                case "admission_time":
                    clinicEmrDto.setAdmission_time(value.split("&")[loop]);
                    break;
                case "hospital":
                    clinicEmrDto.setHospital(value.split("&")[loop]);
                    break;
                case "dept":
                    clinicEmrDto.setDept(value.split("&")[loop]);
                    break;
                case "apply_dept":
                    clinicEmrDto.setApply_dept(value.split("&")[loop]);
                    break;
                case "exam_name":
                    clinicEmrDto.setExam_name(value.split("&")[loop]);
                    break;
                case "exam_method":
                    clinicEmrDto.setExam_method(value.split("&")[loop]);
                    break;
                case "sample_category":
                    clinicEmrDto.setSample_category(value.split("&")[loop]);
                    break;
                case "sample_status":
                    clinicEmrDto.setSample_status(value.split("&")[loop]);
                    break;
                case "sample_id":
                    clinicEmrDto.setSample_id(value.split("&")[loop]);
                    break;
                case "sample_time":
                    clinicEmrDto.setSample_time(value.split("&")[loop]);
                    break;
                case "receive_time":
                    clinicEmrDto.setReceive_time(value.split("&")[loop]);
                    break;
                case "exam_time":
                    clinicEmrDto.setExam_time(value.split("&")[loop]);
                    break;
                case "report_time":
                    clinicEmrDto.setReport_time(value.split("&")[loop]);
                    break;
                case "item_name":
                case "item_abbr":
                case "item_result":
                case "item_unit":
                case "item_hint":
                    List resultList = (ArrayList)clinicEmrDto.getResult_info();
                    if(resultList == null){
                        resultList = new ArrayList<ClinicEmrResultInfo>();
                    }
                    resultList = processResultInfo(value.split("&")[loop],resultList,element);
                    clinicEmrDto.setResult_info(resultList);
                    break;
                default:
                    System.out.println("没有相应枚举值");
            }
            emrContentList.set(loop,clinicEmrDto);
        }
        return emrContentList;
    }

    /**
     * 输入 列值、对应元素名,
     * 利用 对象类 构造json
     * @return
     */
    public static String columnToJsonObjectTwo(){
        List<String> elementList= MockDataUtil.getElement();
        List<String> columnList= MockDataUtil.getColumnTwo();

        ClinicDto clinicDto = new ClinicDto();
        Map constructMap = new HashMap<String,Object>();

        List<Object> paramList = new ArrayList<>();
        ClinicEmrDto clinicEmrDto =null;
        ClinicEmrPatientInfo clinicEmrPatientInfo = null;
        List resultList = null;

        for (int i=0; i<elementList.size();i++){
            String element = elementList.get(i);
            switch (element){
                case "action":
                    clinicDto.setAction(columnList.get(i));
                    break;
                case "type":
                    List type = new ArrayList<>();
                    for(int typeLength=0;typeLength< columnList.get(i).split(";").length;typeLength++){
                        type.add(columnList.get(i).split(";")[typeLength]);
                    }
                    clinicDto.setType(type);
                    break;
                case "param_visit_id":
                    Map paramVisitMap = new HashMap();
                    paramVisitMap.put("name","visit_id");
                    paramVisitMap.put("value",columnList.get(i));
                    paramList.add(paramVisitMap);
                    break;
                case "param_patient_id":
                    Map paramPatientMap = new HashMap();
                    paramPatientMap.put("name","patient_id");
                    paramPatientMap.put("value",columnList.get(i));
                    paramList.add(paramPatientMap);
                    break;
                case "record_id":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setRecord_id(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "record_type":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setRecord_type(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "medical_id":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setMedical_id(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "visit_id":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setVisit_id(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "patient_info_id":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)getConstruts(constructMap,"clinicEmrPatientInfo");
                    clinicEmrPatientInfo.setId(columnList.get(i));
                    constructMap.put("clinicEmrPatientInfo",clinicEmrPatientInfo);
                    break;
                case "patient_info_age":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)getConstruts(constructMap,"clinicEmrPatientInfo");
                    clinicEmrPatientInfo.setAge(columnList.get(i));
                    constructMap.put("clinicEmrPatientInfo",clinicEmrPatientInfo);
                    break;
                case "patient_info_gender":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)getConstruts(constructMap,"clinicEmrPatientInfo");
                    clinicEmrPatientInfo.setGender(columnList.get(i));
                    constructMap.put("clinicEmrPatientInfo",clinicEmrPatientInfo);
                    break;
                case "patient_info_birth_date":
                    clinicEmrPatientInfo = (ClinicEmrPatientInfo)getConstruts(constructMap,"clinicEmrPatientInfo");
                    clinicEmrPatientInfo.setBirth_date(columnList.get(i));
                    constructMap.put("clinicEmrPatientInfo",clinicEmrPatientInfo);
                    break;
                case "record_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setRecord_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "admission_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setAdmission_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "hospital":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setHospital(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "dept":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setDept(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "apply_dept":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setApply_dept(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "exam_name":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setExam_name(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "exam_method":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setExam_method(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "sample_category":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setSample_category(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "sample_status":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setSample_status(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "sample_id":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setSample_id(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "sample_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setSample_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "receive_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setReceive_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "exam_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setExam_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "report_time":
                    clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
                    clinicEmrDto.setReport_time(columnList.get(i));
                    constructMap.put("clinicEmrDto",clinicEmrDto);
                    break;
                case "item_name":
                case "item_abbr":
                case "item_result":
                case "item_unit":
                case "item_hint":
                    resultList = (ArrayList)getConstruts(constructMap,"clinicEmrResultInfo");
                    resultList = processResultInfo(columnList.get(i),resultList,element);
                    constructMap.put("clinicEmrResultInfo",resultList);
                    break;
                default:
                    System.out.println("没有该值");
            }
        }

        clinicDto.setParams(paramList);

        clinicEmrDto = (ClinicEmrDto)getConstruts(constructMap,"clinicEmrDto");
        clinicEmrPatientInfo = (ClinicEmrPatientInfo)getConstruts(constructMap,"clinicEmrPatientInfo");
        resultList = (ArrayList)getConstruts(constructMap,"clinicEmrResultInfo");
        clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
        clinicEmrDto.setResult_info(resultList);
        clinicDto.addEmr(clinicEmrDto);

        return JSON.toJSONString(clinicDto);
    }

    private static List processResultInfo(String value, List resultList, String element){
        ClinicEmrResultInfo clinicEmrResultInfo;
        int resultInfoLength = value.split(";").length;
        for(int loop=0;loop<resultInfoLength;loop++){
            if(loop>= resultList.size()){
                clinicEmrResultInfo = new ClinicEmrResultInfo();
                resultList.add(clinicEmrResultInfo);
            }else{
                clinicEmrResultInfo = (ClinicEmrResultInfo)resultList.get(loop);
            }
            switch (element){
                case "item_name":
                    clinicEmrResultInfo.setItem_name(value.split(";")[loop]);
                    break;
                case "item_abbr":
                    clinicEmrResultInfo.setItem_abbr(value.split(";")[loop]);
                    break;
                case "item_result":
                    clinicEmrResultInfo.setItem_result(value.split(";")[loop]);
                    break;
                case "item_unit":
                    clinicEmrResultInfo.setItem_unit(value.split(";")[loop]);
                    break;
                case "item_hint":
                    clinicEmrResultInfo.setItem_hint(value.split(";")[loop]);
                    break;
            }
            resultList.set(loop,clinicEmrResultInfo);
        }
        return resultList;
    }
    private static Object getConstruts(Map constructMap, String element){
        Object object = null;
        switch (element){
            case "clinicEmrDto":
                if(constructMap.containsKey(element)){
                    object = constructMap.get(element);
                }else{
                    object = new ClinicEmrDto();
                }
                break;
            case "clinicEmrPatientInfo":
                if(constructMap.containsKey(element)){
                    object = constructMap.get(element);
                }else{
                    object = new ClinicEmrPatientInfo();
                }
                break;
            case "clinicEmrResultInfo":
                if(constructMap.containsKey(element)){
                    object = constructMap.get(element);
                }else{
                    object = new ArrayList<Object>();
                }
                break;
            default:
                System.out.println("getConstruts没有此枚举值："+element);
        }


        return object;
    }

    /**
     * json转map
     */
    private static void jsonToMap() {
        String str="{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";
        Map maps=(Map)JSON.parse(str);
        System.out.println("这个是用JSON类来解析JSON字符串!!!");
        for(Object map:maps.entrySet()){
            System.out.println(((Map.Entry)map).getKey()+":"+((Map.Entry)map).getValue());
        }
    }

    /**
     * 输入 列值,
     * 利用 对象类 构造json
     * @return
     */
    public static String columnToJsonObject(){
        List<String> columnList = MockDataUtil.getColumn();
        System.out.println(columnList.size());

        ClinicDto clinicDto = new ClinicDto();
        clinicDto.setAction("save_emr");
        ClinicEmrDto clinicEmrDto = new ClinicEmrDto();
        clinicEmrDto.setRecord_id(columnList.get(1));
        clinicEmrDto.setRecord_type(columnList.get(2));
        clinicEmrDto.setMedical_id(columnList.get(3));
        clinicEmrDto.setVisit_id(columnList.get(4));
        ClinicEmrPatientInfo clinicEmrPatientInfo = new ClinicEmrPatientInfo(columnList.get(5),columnList.get(6),columnList.get(7),columnList.get(8));
        clinicEmrDto.setPatient_info(clinicEmrPatientInfo);
        clinicEmrDto.setRecord_time(columnList.get(9));
        clinicEmrDto.setAdmission_time(columnList.get(10));
        clinicEmrDto.setHospital(columnList.get(11));
        clinicEmrDto.setDept(columnList.get(12));
        clinicEmrDto.setApply_dept(columnList.get(13));
        clinicEmrDto.setExam_name(columnList.get(14));
        clinicEmrDto.setExam_method(columnList.get(15));
        clinicEmrDto.setSample_category(columnList.get(16));
        clinicEmrDto.setSample_status(columnList.get(17));
        clinicEmrDto.setSample_id(columnList.get(18));
        clinicEmrDto.setSample_time(columnList.get(19));
        clinicEmrDto.setReceive_time(columnList.get(20));
        clinicEmrDto.setExam_time(columnList.get(21));
        clinicEmrDto.setReport_time(columnList.get(22));
        System.out.println(columnList.get(23).split(";").length);
        ClinicEmrResultInfo clinicEmrResultInfo;
        for(int i=0;i<columnList.get(23).split(";").length;i++){
            clinicEmrResultInfo = new ClinicEmrResultInfo(columnList.get(23).split(";")[i],
                    columnList.get(24).split(";")[i],
                    columnList.get(25).split(";")[i],
                    columnList.get(26).split(";")[i],
                    columnList.get(27).split(";")[i]);
            clinicEmrDto.addResultInfo(clinicEmrResultInfo);
        }
        clinicDto.addEmr(clinicEmrDto);
        return JSON.toJSONString(clinicDto);
    }

    /**
     * 输入 列值, 自定义json格式构造json
     * @return
     */
    private static String columnToJsonDefine() throws MyException {
        List<String> columnList = MockDataUtil.getColumn();
        String jsonString = MockDataUtil.getJsonDefineTwo();
        JsonDefine jsonDefine = JSON.parseObject(jsonString, JsonDefine.class);

        List<ColumnDefine> columnInformation = jsonDefine.getColumn();

        int columnListIndex = 0;
        String element = "";
        String type = "";
        Map<String,Object> map = new HashMap<String,Object>();
        Stack<JsonStackInfo> stack = new Stack<JsonStackInfo>();

        for( int totalIndex = 0; totalIndex < columnInformation.size(); totalIndex++){
            element = columnInformation.get(totalIndex).getElement() ;
            type = columnInformation.get(totalIndex).getType();
            Object object = getTypeObject(type,columnList,columnListIndex);
            if(type.equals("1")){
                columnListIndex = columnListIndex + 1;
            }
            //开头标志直接进栈
            if(type.equals("2") || type.equals("4") ){
                JsonStackInfo jsonStackInfo = new JsonStackInfo(element,type,object);
                stack.push(jsonStackInfo);
                continue;
            }
            //根据栈是否未null进行处理
            if(stack.empty()){ //栈空
                if(type.equals("1")){
                    map.put(element,object);
                }else{
                    throw new MyException("开始标志不对");
                }
            }else{ //栈不空
                JsonStackInfo jsonStackInfoIn = stack.pop();
                boolean isEnd = (type.equals("5") || type.equals("3"))?true:false;
                if(isEnd){ //type == "1"
                    if((type.equals("5") && jsonStackInfoIn.getElementType() .equals("4")) || (type.equals("3") && jsonStackInfoIn.getElementType() .equals("2"))){
                        if(stack.empty()){
                            map.put(jsonStackInfoIn.getElementName(),jsonStackInfoIn.getObjectValue());
                        }else{
                            JsonStackInfo jsonStackInfoOut = stack.pop();
                            if (jsonStackInfoOut.getElementType() .equals("2")){
                                Map subMap= (HashMap)jsonStackInfoOut.getObjectValue();
                                subMap.put(jsonStackInfoIn.getElementName(),jsonStackInfoIn.getObjectValue());
                                JsonStackInfo jsonStackInfo = new JsonStackInfo(jsonStackInfoOut.getElementName(),jsonStackInfoOut.getElementType(),subMap);
                                stack.push(jsonStackInfo);
                            }else if(jsonStackInfoOut.getElementType() .equals("4")){
                                List subList= (ArrayList)jsonStackInfoOut.getObjectValue();
                                subList.add(jsonStackInfoIn.getObjectValue());
                                JsonStackInfo jsonStackInfo = new JsonStackInfo(jsonStackInfoOut.getElementName(),jsonStackInfoOut.getElementType(),subList);
                                stack.push(jsonStackInfo);
                            }else{
                                throw new MyException("栈里数据标志不对,无法存数据");
                            }
                        }
                    }else{
                            throw new MyException("开始结束不匹配");
                    }
                }else{ //非结束
                    if(type .equals("1")){
                        if (jsonStackInfoIn.getElementType() .equals("2")){
                            Map subMap= (HashMap)jsonStackInfoIn.getObjectValue();
                            subMap.put(element,object);
                            JsonStackInfo jsonStackInfo = new JsonStackInfo(jsonStackInfoIn.getElementName(),jsonStackInfoIn.getElementType(),subMap);
                            stack.push(jsonStackInfo);
                        }else if(jsonStackInfoIn.getElementType() .equals("4")){
                            List subList= (ArrayList)jsonStackInfoIn.getObjectValue();
                            subList.add(object);
                            JsonStackInfo jsonStackInfo = new JsonStackInfo(jsonStackInfoIn.getElementName(),jsonStackInfoIn.getElementType(),subList);
                            stack.push(jsonStackInfo);
                        }else{
                            throw new MyException("栈里数据标志不对,无法存原子数据");
                        }
                    }else{
                        throw new MyException("标志不对");
                    }
                }

            }
        }
        return JSON.toJSONString(map);
        /*System.out.println(columnList);
        System.out.println(jsonString);
        System.out.println(jsonDefine.toString());
        System.out.println(jsonDefine.getColumn().get(2).toString());
        System.out.println(jsonDefine.getColumn().get(2).getElement());*/
    }

    private static Object getTypeObject(String type,List<String> columnList,int columnListIndex){
        Object object = null;
        switch(type){
            case "1":
                object = columnList.get(columnListIndex);;
                break;
            case "2":
                object = new HashMap<String,Object>();
                break;
            case "3":
                break;
            case "4":
                object = new ArrayList<>();
                break;
            case "5":
                break;
            default :
                System.out.println("没有此枚举");
                break;
        }
        return object;
    }
}
