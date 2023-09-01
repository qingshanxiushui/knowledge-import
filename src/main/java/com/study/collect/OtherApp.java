package com.study.collect;

import com.alibaba.fastjson.JSONObject;

import java.util.*;



public class OtherApp {
    public static void main( String[] args ){
        //stackSample();
        //indexSet();
        //sortCollections();
        /*Set<String> s = new HashSet<>();
        s.add(null);
        System.out.println(s.toString()+":"+s.size());
        s.add("a");
        System.out.println(s.toString()+":"+s.size());


        List<JSONObject> structuredText = null;
        System.out.println(structuredText==null);
        structuredText = new ArrayList<>();
        System.out.println(structuredText==null);

        Map<String,String> mapOne= new HashMap<>();
        mapOne.put("mapOne","a");
        mapOne.put("mapT",null);

        System.out.println(mapOne.toString());*/

        List<String> list1 = new ArrayList();
        list1.add("222");
        list1.add("111");
        list1.add("1113");
        list1.sort(Comparator.comparing(String::hashCode));
        List<String> list2 = new ArrayList();
        list2.add("222");
        list2.add("1113");
        list2.sort(Comparator.comparing(String::hashCode));
        System.out.println(list1.equals(list2));
        System.out.println(list1.containsAll(list2));

    }

    private static void sortCollections() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(1);
        list.add(5);
        list.add(3);
        list.forEach(System.out::println);
        //利用Collections.sort方法按照年龄排序，默认升序
        Collections.sort(list, (a,b)->{
            return a - b;
        });
        System.out.println("================================");
        list.forEach(System.out::println);
    }

    private static void indexSet() {
        List<Integer> offsetBeginList = new ArrayList<>(2);
        offsetBeginList.add(Integer.MAX_VALUE);
        offsetBeginList.add(0);
        offsetBeginList.set(0,5);
        System.out.println(offsetBeginList.toString());
        System.out.println("qerwteyr".indexOf("w"));
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
