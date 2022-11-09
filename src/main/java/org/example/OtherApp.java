package org.example;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class OtherApp {
    public static void main( String[] args ){
        //googleMethodOne();
        streamFunctionOne();
        //streamFunctionTwo();

    }

    private static void googleMethodOne() {
        System.out.println(LOWER_CAMEL.toString());
        System.out.println(UPPER_CAMEL.toString());
        String methodName = "method" + LOWER_CAMEL.to(UPPER_CAMEL, "input");
        System.out.println(methodName);
    }

    private static void streamFunctionTwo() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("张三丰");
        list.add("张三");
        list.add("李四");
        list.add("孙悟空");
        list.add("张一飞");
        //需求1：取前4个数据组成一个流
        Stream<String> limitStream = list.stream().limit(4);
        //需求2：跳过2个数据组成一个流
        Stream<String> skipStream = list.stream().skip(2);
        //需求3：合并需求1和需求2得到的流，并把结果在控制台输出
//        Stream.concat(limitStream,skipStream).forEach(System.out::println);
        //需求4：合并需求1和需求2得到的流，并把结果在控制台输出，要求字符串元素不能重复
        Stream.concat(limitStream,skipStream).distinct().forEach(System.out::println);
    }

    private static void streamFunctionOne() {


        ArrayList<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("张三丰");
        list.add("张三");
        list.add("李四");
        list.add("孙悟空");
        list.add("张一飞");

        ArrayList<String> zhangList = new ArrayList<String>();
        for (String s : list) {
            if (s.startsWith("张")) {
                zhangList.add(s);
            }
        }
        ArrayList<String> treeList = new ArrayList<String>();
        for (String s : zhangList) {
            if (s.length() == 3) {
                treeList.add(s);
            }
        }
        for (String s : treeList) {
            System.out.println(s);
        }
        System.out.println("-------------------------------");
        //Stream流改进
        list.stream().filter(s -> s.startsWith("张")).filter(s -> s.length() == 3).forEach(s -> System.out.println(s));
    }


}
