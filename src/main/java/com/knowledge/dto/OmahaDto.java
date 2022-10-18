package com.knowledge.dto;

import com.knowledge.entity.DiagnosticFactorEntity;
import com.knowledge.entity.OmahaEntity;
import lombok.Data;

import java.util.ArrayList;

@Data
public class OmahaDto {
    private String diease;
    private ArrayList<OmahaEntity> identifyList = new ArrayList<OmahaEntity>(); //鉴别诊断
    private ArrayList<OmahaEntity> positiveSymptomList = new ArrayList<OmahaEntity>(); //阳性症状
    private ArrayList<OmahaEntity> positiveSignAList = new ArrayList<OmahaEntity>(); //阳性体征A
    private ArrayList<OmahaEntity> positiveInspectAList = new ArrayList<OmahaEntity>(); //阳性检查A
    private ArrayList<OmahaEntity> positiveExamineAList = new ArrayList<OmahaEntity>(); //阳性检验A
}
