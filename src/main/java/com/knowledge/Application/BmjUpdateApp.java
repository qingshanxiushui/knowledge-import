package com.knowledge.Application;

import com.knowledge.dto.DiagnosisDto;
import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.util.BmjDataProcess;
import com.knowledge.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//与APP功能相同
public class BmjUpdateApp {
    public static void main( String[] args ) throws IOException {

        HashMap<String, DiagnosisDto> diagnosisMap = new HashMap<String,DiagnosisDto>();
        List<DiagnosisResultDto> diagnosisResultList = new ArrayList<DiagnosisResultDto>();
        int serialNoExcel = 1;

        //诊断因素
        ArrayList<File> zhenDuanfileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-诊断因素",zhenDuanfileList);
        for(File file :zhenDuanfileList){
            String jsonStr = FileUtil.readJsonFile(file.toString());
            BmjDataProcess.diagnosticProcess(jsonStr,diagnosisMap);
        }

        //手术
        ArrayList<File> shouShufileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-手术",shouShufileList);
        for(File file :shouShufileList){
            String jsonStr = FileUtil.readJsonFile(file.toString());
            BmjDataProcess.operationProcess(jsonStr,diagnosisMap);
        }
        //System.out.println(diagnosisMap.get("lambert-eaton肌无力综合征"));

        //手术
        ArrayList<File> liangBiaofileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-量表",liangBiaofileList);
        for(File file :liangBiaofileList){
            String jsonStr = FileUtil.readJsonFile(file.toString());
            BmjDataProcess.scaleProcess(jsonStr,diagnosisMap);
        }
        //System.out.println(diagnosisMap.get("阿尔茨海默病"));

        //鉴别诊断
        ArrayList<File> jianBieFileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-鉴别诊断",jianBieFileList);
        for(File file :jianBieFileList){
            String jsonStr = FileUtil.readJsonFile(file.toString());
            BmjDataProcess.differentialDiagnosisProcess(jsonStr,diagnosisMap);
        }
        //System.out.println(diagnosisMap.get("addison病"));

        //检查
        ArrayList<File> jianChaFileList = new ArrayList<File>();
        FileUtil.fileList("E:\\医疗\\知识导入\\知识导入任务\\BMJ\\BMJ-检查",jianChaFileList);
        for(File file :jianChaFileList){
            String jsonStr = FileUtil.readJsonFile(file.toString());
            BmjDataProcess.positiveInspectProcess(jsonStr,diagnosisMap);
        }
        //System.out.println(diagnosisMap.get("addison病"));


        //数据打平
        //System.out.println(diagnosisMap.size());
        for(String key:diagnosisMap.keySet()){
            BmjDataProcess.diagnosisFlatMap(diagnosisMap.get(key),diagnosisResultList,serialNoExcel);
            serialNoExcel = serialNoExcel+1;
        }
        //BmjDataProcess.diagnosisFlatMap(diagnosisMap.get("addison病"),diagnosisResultList,serialNoExcel);
        //System.out.println(diagnosisResultList.toString());
        System.out.println(diagnosisResultList.size());

        FileUtil.exportLocalExcel(diagnosisResultList,"E:\\医疗\\知识导入\\知识导入任务\\diagnosis-export-update.xlsx");

    }
}
