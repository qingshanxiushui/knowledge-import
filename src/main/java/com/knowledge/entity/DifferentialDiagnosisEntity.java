package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DifferentialDiagnosisEntity {

    private String name;
    private ArrayList<String> subject;
    private ArrayList<DifferentialDiagnosisSubEntity> 鉴别诊断;

}
