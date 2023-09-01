package com.study.string;

import java.util.Formatter;

public class FormatterUtil {
    private Formatter fmt;

    public void initial(){
        fmt = new Formatter();
    }

    public String formatterString(String s1, String s2,String s3){
        fmt.format("%s 的 %s 是 %s %n",s1, s2, s3);
        return fmt.toString();
    }

    public void colse(){
        fmt.close();
    }

    public String stringFormatter(String s1, String s2,String s3){
        return String.format("%s 的 %s 是 %s ",s1, s2, s3);
    }

    public static void main( String[] args ){
        String text = String.format("%s的%s是%s", "结直肠癌", "鉴别诊断", "溃疡性结肠炎");
        System.out.println(text);

        FormatterUtil formatterUtil = new FormatterUtil();
        formatterUtil.initial();
        System.out.println(formatterUtil.formatterString("我","电脑","联想"));
        System.out.println(formatterUtil.formatterString("你","电脑","苹果"));
        System.out.println(formatterUtil.formatterString("他","电脑","华硕"));
        String s = "qazwsx";
        String name = "John";
        int age = 25;
        System.out.println(String.format("My name is %s and I am %d years old. %n", name, age));
        formatterUtil.colse();
        System.out.println(formatterUtil.stringFormatter("我","电脑","联想"));
        System.out.println(formatterUtil.stringFormatter("你","电脑","苹果"));
        System.out.println(formatterUtil.stringFormatter("他","电脑","华硕"));
    }
}
