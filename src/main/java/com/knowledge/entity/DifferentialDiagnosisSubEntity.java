package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DifferentialDiagnosisSubEntity {
    private String 疾病;
    private ArrayList<String> 症状;
    private ArrayList<String> 检查;
}
