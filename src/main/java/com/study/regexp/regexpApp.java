package com.study.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java正则表达式使用
 * https://blog.csdn.net/dansam/article/details/88840858
 * https://blog.csdn.net/m0_62618110/article/details/123704869
 */
public class regexpApp {
    public static void main( String[] args ){
        //编译正则表达式，这样子可以重用模式。
        Pattern p = Pattern.compile("a*b");
        // 用模式检查字符串
        Matcher m = p.matcher("aaaaab");
        //检查匹配结果
        boolean b1 = m.matches();
        System.out.println(b1);

        boolean b2 = Pattern.matches("a*b", "aaaaab");
        System.out.println(b2);

        boolean b3 = "aaaaab".matches("a*b");
        System.out.println(b3);
    }

}
