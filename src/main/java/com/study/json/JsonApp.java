package com.study.json;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonApp {

    public static void main( String[] args ){

        JSONObject a = new JSONObject();
        a.put("test",null);
        System.out.println(a);
        System.out.println(a.get("test"));
        JSONObject c = new JSONObject();
        a.put("offset",c);
        JSONArray b = new JSONArray();
        c.put("name",b);
        b.add(17);
        b.add(20);
        System.out.println(a.getJSONObject("offset").getJSONArray("name").get(0));

    }


}
