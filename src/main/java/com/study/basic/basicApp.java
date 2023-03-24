package com.study.basic;

import groovy.lang.GroovyClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class basicApp {
    public static void main( String[] args ) throws Exception {
        //dynamicClass();
        testGroovyClasses();
    }

    private static void testGroovyClasses() throws Exception {
        //groovy提供了一种将字符串文本代码直接转换成Java Class对象的功能
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
        Class<?> clazz = groovyClassLoader.parseClass("package com.study.basic;\n" +
                "\n" +
                "public class Main {\n" +
                "\n" +
                "    public int age = 22;\n" +
                "    \n" +
                "    public void sayHello() {\n" +
                "        System.out.println(\"年龄是:\" + age);\n" +
                "    }\n" +
                "    public void sayHelloTwo(Integer num) {\n" +
                "        System.out.println(\"年龄2是:\" + num);\n" +
                "    }\n" +
                "}\n");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sayHello");
        method.invoke(obj);
        Method methodTwo = clazz.getDeclaredMethod("sayHelloTwo",Integer.class);
        methodTwo.invoke(obj,33);

        Object val = method.getDefaultValue();
        System.out.println(val);
    }

    private static void dynamicClass() throws Exception {
        // Java 源代码
        String sourceStr = "public class Hello{public String sayHello(String name){ return \"Hello,\"+name+\"!\";}}";
        // 类名及文件名
        String clsName = "Hello";
        // 方法名
        String methodName = "sayHello";
        // 当前编译器
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();
        // Java标准文件管理器
        StandardJavaFileManager fm = cmp.getStandardFileManager(null, null,
                null);
        // Java 文件对象
        JavaFileObject jfo = new StringJavaObject(clsName, sourceStr);

        // 编译参数，类似于Javac <options> 中的options
        List<String> optionsList = new ArrayList<String>();

        // 编译文件的存放地方，注意：此处是为Eclipse 工具特设的
        optionsList.addAll(Arrays.asList("-d", "./src/main/java/com/study/basic"));
        // 要编译的单元
        List<JavaFileObject> jfos = Arrays.asList(jfo);
        // 设置编译环境
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null,
                optionsList, null, jfos);
        // 编译成功
        if (task.call()) {
            // 生成对象
            Object obj = Class.forName(clsName).newInstance();
            Class<? extends Object> cls = obj.getClass();
            // 调用sayHello方法
            Method m = cls.getMethod(methodName, String.class);
            String str = (String) m.invoke(obj, "Dynamic Compilation");
            System.out.println(str);
        }
    }
}
