package com.study.guava;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class guavaApp {
    public static void main( String[] args ){
        googleMethodOne();
    }
    private static void googleMethodOne() {
        System.out.println(LOWER_CAMEL.toString());
        System.out.println(UPPER_CAMEL.toString());
        String methodName = "method" + LOWER_CAMEL.to(UPPER_CAMEL, "input");
        System.out.println(methodName);
    }
}
