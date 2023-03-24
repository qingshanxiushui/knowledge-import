package com.study.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestCallerUtil {
    //get方式请求
    public String restCallerGet(String path, String param) {
        //path 接口路径 xxx/xxx/xxx
        //param 入参 ?xxx=x&xxx=x&xxx=x
        //接口ip
        String httpip = "http://127.0.0.1:8088";
        String data = "";

        //url拼接
        String lasturl = httpip + path + param;
        System.out.println("url:"+lasturl);
        try {
            URL url = new URL(lasturl);
            //打开和url之间的连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            //请求头
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            urlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("GET");//GET和POST必须全大写
            urlConn.connect();

            int code = urlConn.getResponseCode();//获得响应码
            System.out.println("响应码："+code);
            if (code == 200) {//响应成功，获得响应的数据
                //InputStream is = urlConn.getInputStream();//得到数据流（输入流）
                //byte[] buffer = new byte[1024];
                //int length = 0;
                //while ((length = is.read(buffer)) != -1) {
                //	String res = new String(buffer, 0, length);
                //	data += res;
                //}
                //System.out.println(data);

                //解决中文乱码
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
                data = reader.readLine();
                System.out.println("响应数据："+data);
            }
            urlConn.disconnect();   //断开连接

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    //post方式请求
    public String restCallerPost(String httpip,String path,String urlParam ,String param) throws UnsupportedEncodingException {
        //path 接口路径 xxx/xxx/xxx
        //param 入参json {}
        //接口ip
        int responseCode;
        //String urlParam = "?aaa=1&bbb=2";
        String data = "";
        //url拼接
        String lasturl = httpip + path + new String(urlParam.getBytes("iso8859-1"),"utf-8");
        System.out.println("url:"+lasturl);
        try {
            URL restURL = new URL(lasturl);
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(20*1000);
            conn.setReadTimeout(20*1000);
            conn.connect();
            System.out.println("参数："+param);
            //输入流
            //解决中文乱码
            /*OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            os.write(param);
            os.flush();
            os.close();*/
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            byte[] t = param.getBytes("utf-8");
            dataOutputStream.write(t);
            dataOutputStream.flush();
            dataOutputStream.close();
            // 输出response code
            responseCode = conn.getResponseCode();
            System.out.println("响应码："+responseCode);
            // 输出response
            if (responseCode == 200) {
                //输出流
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //解决中文乱码
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                data = reader.readLine();
            } else {
                data = "false";
            }
            // 断开连接
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
