package com.study.http;

import com.alibaba.fastjson.JSON;
import com.study.http.dto.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java调用REST接口（get，post请求方法）
 * https://blog.csdn.net/dante_feng/article/details/118365205
 * Java学习--HttpURLConnection发送post请求
 * https://blog.csdn.net/qq_41117947/article/details/79361094
 */
public class Connect {
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //httpConect();
        //httpSoapConect();
        httpClinicConnect();
    }



    private static void httpClinicConnect() throws UnsupportedEncodingException {

        String httpip = "http://10.33.19.214";
        String pathPost = "/medkb-api/recommend";

        //构造body请求参数
        String paramPost = "{\n" +
                "    \"action\": \"save_emr\",\n" +
                "    \"type\":[],\n" +
                "    \"params\":[],\n" +
                "    \"emr_contents\": [\n" +
                "        {\"record_id\":\"sd2313213123213\",\n" +
                "        \"record_type\":\"检验记录\",\n" +
                "        \"medical_id\":\"da324141\",\n" +
                "        \"visit_id\":\"ZY1232132131\",\n" +
                "        \"patient_info\":{\n" +
                "            \"id\":\"2313123213\",\n" +
                "            \"age\":\"13岁\",\n" +
                "            \"gender\":\"1\",\n" +
                "            \"birth_date\":\"2008-11-1\"\n" +
                "        },\n" +
                "        \"record_time\":\"2021-10-23 11:11:11\",\n" +
                "        \"admission_time\":\"2021-10-23 08-23-25\",\n" +
                "        \"hospital\":\"广医附一\",\n" +
                "        \"dept\":\"心血管内科\",\n" +
                "        \"apply_dept\":\"心血管内科\",\n" +
                "        \"exam_name\":\"血常规\",\n" +
                "        \"exam_method\":\"血液标本\",\n" +
                "        \"sample_category\":\"血液\",\n" +
                "        \"sample_status\":\"合格\",\n" +
                "        \"sample_id\":\"23213\",\n" +
                "        \"sample_time\":\"2021-10-23 11:05:09\",\n" +
                "        \"receive_time\":\"2021-10-23 11:10:09\",\n" +
                "        \"exam_time\":\"2021-10-23 11:12:09\",\n" +
                "        \"report_time\":\"2021-10-23 11:15:09\",\n" +
                "        \"result_info\":[{\n" +
                "            \"item_name\":\"血红蛋白\",\n" +
                "            \"item_abbr\":\"Hb\",\n" +
                "            \"item_result\":\"153\",\n" +
                "            \"item_unit\":\"g/L\",\n" +
                "            \"item_hint\":\"高\"\n" +
                "        },{\n" +
                "            \"item_name\":\"白蛋白\",\n" +
                "            \"item_abbr\":\"albumin\",\n" +
                "            \"item_result\":\"70\",\n" +
                "            \"item_unit\":\"g/dl\",\n" +
                "            \"item_hint\":\"高\"\n" +
                "        }]}\n" +
                "    ]\n" +
                "}";
        //System.out.println(paramPost);
        //paramPost = MockDataUtil.getMapJson();
        //paramPost = MockDataUtil.getObjectJson();
        paramPost = ColumnToJson.columnToJsonObject();
        //发送http连接,RestCallerUtil为自行封装的工具类
        RestCallerUtil rcuPost = new RestCallerUtil();
        String resultDataPost = rcuPost.restCallerPost(httpip,pathPost, "",paramPost);
        System.out.println(resultDataPost);
    }

    private static void httpSoapConect() throws IOException {
        //参考 https://blog.csdn.net/weixin_28882149/article/details/114459801
        String urlString = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
        String xmlFile = "soap1.2.xml";// 要发送的soap格式文件   参考 http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?op=getWeatherbyCityName
        //String soapActionString = "http://WebXml.com.cn/getWeatherbyCityName";  //SOAP 1.1需要
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        File fileToSend = new File(xmlFile);
        byte[] buf = new byte[(int) fileToSend.length()];// 用于存放文件数据的数组
        new FileInputStream(xmlFile).read(buf);
        httpConn.setRequestProperty("Content-Length",String.valueOf(buf.length));//这句话可以不用写，即使是随便写,根据我的测试，过去的请求头中的Content-Length长度也是正确的，也就是说它会自动进行计算
        httpConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        /*httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8"); //SOAP 1.1需要
        httpConn.setRequestProperty("soapActionString",soapActionString);//Soap //SOAP 1.1需要 */
//        httpConn.setRequestProperty("Connection", "Keep-Alive");
//        httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        httpConn.setRequestProperty("accept","*/*"); //此处为暴力方法设置接受所有类型，以此来防范返回415;

        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        OutputStream out = httpConn.getOutputStream();
        out.write(buf);
        out.close();

        InputStreamReader is = new InputStreamReader(httpConn.getInputStream(),"utf-8");
        BufferedReader in = new BufferedReader(is);
        String inputLine;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.xml")));// 将结果存放的位置
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            bw.write(inputLine);
            bw.newLine();
        }
        bw.close();
        in.close();
        httpConn.disconnect();

    }

    private static void httpConect() throws UnsupportedEncodingException {
        //get请求
        //httpGetMethod();

        //post请求json传入
        //httpPostJsonMethod();

        //post_params请求
        //httpPostParamsMethod();

        //object装json
        httpPostColumnToJson();

    }

    private static void httpPostColumnToJson() throws UnsupportedEncodingException {
        AllData allData = new AllData();

        Record record1 = new Record();
        Column column11 = new Column(11);
        Column column12 = new Column("string1A");
        Column column13 = new Column("string1B");
        record1.addColumn(column11);
        record1.addColumn(column12);
        record1.addColumn(column13);

        Record record2 = new Record();
        Column column21 = new Column(22);
        Column column22 = new Column("string2A");
        record2.addColumn(column21);
        record2.addColumn(column22);

        allData.addRecord(record1);
        allData.addRecord(record2);

        String allJson = JSON.toJSONString(allData);
        System.out.println(allJson);

        String httpip = "http://127.0.0.1:8088";
        String pathPostParam = "/hello/json";
        String urlParam = "";
        RestCallerUtil rcuPostParam = new RestCallerUtil();
        String resultDataPostParam = rcuPostParam.restCallerPost(httpip, pathPostParam, urlParam, allJson);
        System.out.println(resultDataPostParam);
    }

    private static void httpPostParamsMethod() throws UnsupportedEncodingException {
        String httpip = "http://127.0.0.1:8088";
        String pathPostParam = "/hello/template";
        String urlParam = "?code=200&message=中文";
        String str ="";
        RestCallerUtil rcuPostParam = new RestCallerUtil();
        String resultDataPostParam = rcuPostParam.restCallerPost(httpip,pathPostParam, urlParam,str);
        System.out.println(resultDataPostParam);
    }

    private static void httpPostJsonMethod() throws UnsupportedEncodingException {
        String httpip = "http://127.0.0.1:8088";
        String pathPost = "/hello/student";
        String paramPost = "{\"id\":4,\"enable\":5,\"chinese\":\"中文\"}";
        //RestCallerUtil为自行封装的工具类
        System.out.println(paramPost);
        RestCallerUtil rcuPost = new RestCallerUtil();
        String resultDataPostOne = rcuPost.restCallerPost(httpip,pathPost, "",paramPost);
        System.out.println(resultDataPostOne);
        String paramPostTwo = "{\"id\":9,\"enable\":8,\"chinese\":\"英文\"}";
        String resultDataPostTwo = rcuPost.restCallerPost(httpip,pathPost, "",paramPostTwo);
        System.out.println(resultDataPostTwo);
    }

    private static void httpGetMethod() {
        String pathGet = "/hello/rule";
        String paramGet = "";
        //RestCallerUtil为自行封装的工具类
        RestCallerUtil rcuGet = new RestCallerUtil();
        String resultDataGet = rcuGet.restCallerGet(pathGet, paramGet);
        System.out.println(resultDataGet);
    }

    /**
     * url编码
     *
     * @param url
     * @return 编码后的字符串,当异常时返回原始字符串。
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url,  "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return url;
        }

    }
}
