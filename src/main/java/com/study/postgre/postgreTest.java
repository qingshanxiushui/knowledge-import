package com.study.postgre;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class postgreTest {
    public static void main(String[] args) throws IOException {

        accessPostgre();
    }

    public static JSONArray accessPostgre() throws IOException {
        //获取访问数据库属性
        Map<String,String> propertyMap = getProperties();
        //构造方法
        postgreUtil util=new postgreUtil(propertyMap.get("userName"),propertyMap.get("passWord"),
                propertyMap.get("ipAddress"),propertyMap.get("databaseName"),propertyMap.get("port"));
        //postgreUtil util=new postgreUtil("postgres","CareRec@2021","10.33.19.214","carerec","5432");
        //查询
        Map<String,HashMap<String, Object>> select = util.Select("select eti.id as info_id, eti.emr_type as emr_type, eti.\"version\" as \"version\" , ed.id as definition_id, ed.field_info as field_info from emr_type_info eti  join emr_definition ed on eti.id = ed.emr_type_id and eti.\"version\" = ed.\"version\"  where eti.deleted = 0 and eti.usable = 1");
        //关流
        util.close();
        //System.out.println(select);
        //System.out.println(select.get("检验记录")); 检验记录 入院记录
        select.get("入院记录").get("field_info");
        JSONArray emrContext = JSONObject.parseArray((String) select.get("入院记录").get("field_info"));
        int columnCount =0 ;
        for (Object emrObject : emrContext){
            JSONObject emr = (JSONObject)emrObject;
            if(((String)emr.get("data_type")).equals("object") || ((String)emr.get("data_type")).equals("array")){
                //System.out.println("OA" + (String) emr.get("name") + ":"+ (String)emr.get("data_type") + ":"+ (String)emr.get("field_name"));
                for (Object patientObject : (JSONArray)emr.get("item")){

                    JSONObject patient = (JSONObject)patientObject;
                    //System.out.println((String) patient.get("name") + ":"+ (String)patient.get("data_type") + ":"+ (String)patient.get("field_name"));
                    columnCount++;
                }
            }else{
                //System.out.println((String) emr.get("name") + ":"+ (String)emr.get("data_type") + ":"+ (String)emr.get("field_name"));
                columnCount++;
            }
        }
        //System.out.println("列数量"+columnCount);
        return emrContext;
    }

    public static Map<String,String> getProperties() throws IOException {
        Properties prop = new Properties();
        //InputStream input = new FileInputStream("./src/resources/config.properties");
        InputStream input =  postgreTest.class.getResourceAsStream("/config.properties");
        prop.load(input);
        Map<String,String> propertyMap = new HashMap<>();
        propertyMap.put("ipAddress",prop.getProperty("ipAddress"));
        propertyMap.put("port",prop.getProperty("port"));
        propertyMap.put("databaseName",prop.getProperty("databaseName"));
        propertyMap.put("userName",prop.getProperty("userName"));
        propertyMap.put("passWord",prop.getProperty("passWord"));
        System.out.println(propertyMap);
        return propertyMap;
    }
}
