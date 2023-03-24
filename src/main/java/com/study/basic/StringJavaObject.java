package com.study.basic;


import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

// 文本中的Java对象
public class StringJavaObject extends SimpleJavaFileObject {
    // 源代码
    private String content = "";

    // 遵循Java规范的类名及文件
    public StringJavaObject(String _javaFileName, String _content) {
        super(_createStringJavaObjectUri(_javaFileName), Kind.SOURCE);
        content = _content;
    }

    // 产生一个URL资源库
    private static URI _createStringJavaObjectUri(String name) {
        // 注意此处没有设置包名
        return URI.create("String:///" + name + Kind.SOURCE.extension);
    }

    // 文本文件代码
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        // TODO Auto-generated method stub
        return content;
    }
}
