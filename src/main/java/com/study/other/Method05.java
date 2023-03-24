package com.study.other;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// 方法引用
public class Method05 {
    public static void main(String[] args) {
        // 原始写法
        doSth("doOne");
        // 方法引用
        doSthNew("doOne");
    }

    // 原始写法
    private static void doSth(String ds) {
        if("doOne".equals(ds)){
            new DoSth().doOne();
        }else if("doTwo".equals(ds)){
            new DoSth().doTwo();
        }else if("doThree".equals(ds)){
            new DoSth().doThree();
        }else {
            System.out.println("啥也不干！");
        }
    }

    // 方法引用
    private static void doSthNew(String ds){
        Map<String, Consumer<DoSth>> functionMap = new HashMap<>();
        functionMap.put("doOne", DoSth::doOne);
        functionMap.put("doTwo", DoSth::doTwo);
        functionMap.put("doThree", DoSth::doThree);
        functionMap.get(ds).accept(new DoSth());
    }
}
class DoSth {
    public void doOne() {
        System.out.println("One");
    }

    public void doTwo() {
        System.out.println("Two");
    }

    public void doThree() {
        System.out.println("Three");
    }
}