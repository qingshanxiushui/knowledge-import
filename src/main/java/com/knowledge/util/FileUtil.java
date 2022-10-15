package com.knowledge.util;

import java.io.*;
import java.util.ArrayList;

public class FileUtil {

    public static ArrayList fileList = new ArrayList();

    /**
     * 读取文件
     * @param fileName
     * @return String
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fileList(String filepath) {
        //System.out.println(filepath);
        File file= new File(filepath);
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if(file2.isDirectory()) {
                fileList(file2 + "\\");
            }
            if(!file2.isDirectory()) {
                String fileName = file2.getName();
                if(!fileName.contains("emr") && !fileName.contains("DS_Store")){
                    fileList.add(file2);
                }
            }
        }
    }
}
