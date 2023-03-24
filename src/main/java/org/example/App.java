package org.example;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main( String[] args ){
        Map map = new HashMap();
        map.put("1",1);
        System.out.println(map.get("2") == null);
    }
}
