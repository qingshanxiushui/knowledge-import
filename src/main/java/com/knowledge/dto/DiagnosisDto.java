package com.knowledge.dto;

import com.knowledge.entity.DiagnosticFactorEntity;
import com.knowledge.entity.DifferentialDiagnosisSubEntity;
import com.knowledge.entity.InspectSubEntity;
import com.knowledge.entity.ScaleContentEntity;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DiagnosisDto {
    private String termName;
    private ArrayList<DiagnosticFactorEntity> positiveSymptoms; //阳性症状
    private ArrayList<String> operationsName; //关联手术名称
    private ArrayList<ScaleContentEntity> scaleContent; //量表
    private ArrayList<DifferentialDiagnosisSubEntity> DifferentialDiagnosis; //鉴别诊断
    private ArrayList<InspectSubEntity> positiveInspect; //阳性检查
}
