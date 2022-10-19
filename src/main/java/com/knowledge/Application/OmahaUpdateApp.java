package com.knowledge.Application;

import com.knowledge.dto.DiagnosisResultDto;
import com.knowledge.dto.OmahaDto;
import com.knowledge.entity.OmahaEntity;
import com.knowledge.util.FileUtil;
import com.knowledge.util.OmahaDataProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OmahaUpdateApp {

    public static void main( String[] args ) throws IOException {
        HashMap<String, OmahaDto> omahaDtoHashMap = new HashMap<String, OmahaDto>();
        List<DiagnosisResultDto> omahaDiagnosisResultList = new ArrayList<DiagnosisResultDto>();
        int omahaSerialNoExcel = 1;

        List<OmahaEntity> omahaEntitySubList = FileUtil.readLocalFileDirectSub("E:\\医疗\\知识导入\\知识导入任务\\OMAHA\\汇知医学知识图谱_疾病_20220820-sub.xlsx");
        System.out.println(omahaEntitySubList.size());

        OmahaDataProcess.omahaProcess(omahaEntitySubList,omahaDtoHashMap);
        System.out.println(omahaDtoHashMap.size());

        for(String key: omahaDtoHashMap.keySet()){
            OmahaDataProcess.omahaFlatMap(omahaDtoHashMap.get(key),omahaDiagnosisResultList,omahaSerialNoExcel);
            omahaSerialNoExcel = omahaSerialNoExcel + 1;
        }
        System.out.println(omahaDiagnosisResultList.size());

        FileUtil.exportLocalExcelBig("E:\\医疗\\知识导入\\知识导入任务\\omaha-export-update.xlsx",omahaDiagnosisResultList);

    }

}
