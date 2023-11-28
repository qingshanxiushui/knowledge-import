package com.study.collect;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class OtherApp {
    public static void main( String[] args ){
        //stackSample();
        //indexSet();
        //sortCollections();
        //containsAll();
        //compare();
        multiParam("concept.concept_type", "concept.preferred_term.name");

    }

    private static void multiParam(String... paramNames){
        List<String> params = Stream.of(paramNames).collect(Collectors.toList());
        System.out.println(params.toString());
    }

    private static void compare() {
        Map<Integer,List<String>> map = new HashMap<>();
        List<String> a = new ArrayList<>();
        a.add("b");
        a.add("a");
        a.add("c");
        map.put(1,a);
        List<String> b = new ArrayList<>();
        b.add("c");
        b.add("a");
        b.add("b");
        map.put(2,b);
        for(int key : map.keySet()){
            Collections.sort(map.get(key),new Comparator<String>() {
                @Override
                public int compare(String p1, String p2) {
                    return p2.compareTo(p1);
                }
            });
        }
        System.out.println(map.toString());
    }

    private static void containsAll() {
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
