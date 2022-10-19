package com.knowledge.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.entity.OmahaEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

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

    public static ArrayList<File> fileList(String filepath,ArrayList<File> files) {
        //System.out.println(filepath);
        File file= new File(filepath);
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if(file2.isDirectory()) {
                fileList(file2 + "\\",files);
            }
            if(!file2.isDirectory()) {
                String fileName = file2.getName();
                if(!fileName.contains("emr") && !fileName.contains("DS_Store") && !fileName.contains("graph.txt")){
                    files.add(file2);
                }
            }
        }
        return files;
    }

    public static void exportLocalExcel(List<DiagnosisResultDto> diagnosisResultList,String exportFile) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("诊断","临床表现", ExcelType.XSSF),
                DiagnosisResultDto.class, diagnosisResultList);
        FileOutputStream fos = new FileOutputStream(exportFile);
        workbook.write(fos);
        fos.close();
    }

    public static List<OmahaEntity> readLocalFileDirectSub(String importFile) {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<OmahaEntity> omahaEntitySubList = ExcelImportUtil.importExcel(new File(importFile),OmahaEntity.class, params);
        return omahaEntitySubList;
    }

    public static void exportLocalExcelBig(String exportFile,List<DiagnosisResultDto> omahaDiagnosisResultList) throws IOException {
        int totalPage = (omahaDiagnosisResultList.size() / 10000) + 1;
        int pageSize = 10000;
        Workbook workbookBig = ExcelExportUtil.exportBigExcel(
                new ExportParams("诊断","临床表现", ExcelType.XSSF),
                DiagnosisResultDto.class,
                new IExcelExportServer(){

                    /**
                     * queryParams 就是下面的totalPage，限制条件
                     * page 是页数，他是在分页进行文件转换，page每次+1
                     */
                    @Override
                    public List<Object> selectListForExcelExport(Object queryParams, int page) {
                        //很重要！！这里面整个方法体，其实就是将所有的数据aList分批返回处理
                        //分批的方式很多，我直接用了subList。然后 每批不能太大。我试了30000一批，
                        //内存溢出，貌似 最大每批10000
                        if (page > totalPage) {
                            return null;
                        }
                        // fromIndex开始索引，toIndex结束索引
                        int fromIndex = (page - 1) * pageSize;
                        int toIndex = page != totalPage ? fromIndex + pageSize : omahaDiagnosisResultList.size();
                        List<Object> list = new ArrayList<>();
                        list.addAll(omahaDiagnosisResultList.subList(fromIndex, toIndex));
                        return list;
                    }
                },
                totalPage
        );
        FileOutputStream fosBig = new FileOutputStream(exportFile);
        workbookBig.write(fosBig);
        fosBig.close();
    }
}
