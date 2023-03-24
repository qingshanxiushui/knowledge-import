package com.study.collect;

import java.util.*;



public class OtherApp {
    public static void main( String[] args ){
        stackSample();
    }
    private static void stackSample() {
        Stack<Map> stack = new Stack<Map>();
        Map<String,String> mapOne= new HashMap<String,String>();
        mapOne.put("mapOne","valueOne");
        stack.push(mapOne);
        Map<String,String> mapTwo= new HashMap<String,String>();
        mapTwo.put("mapTwo","valueTwo");
        stack.push(mapTwo);
        Map<String,String> mapThree= new HashMap<String,String>();
        mapThree.put("mapThree","valueThree");
        stack.push(mapThree);
        System.out.println(stack.size());
        for(String key : mapOne.keySet()){
            String value = mapOne.get(key);
            System.out.println(key+" "+value);
        }
        System.out.println(stack.toString());
        System.out.println(stack.pop().toString());//取出不放会
        System.out.println(stack.size());
        System.out.println(stack.peek().toString());//取出放会
        System.out.println(stack.size());
        stack.pop();
        stack.pop();
        System.out.println(stack.size());
        System.out.println(stack.empty());
    }





}
