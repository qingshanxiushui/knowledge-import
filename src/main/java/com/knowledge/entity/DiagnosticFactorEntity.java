package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DiagnosticFactorEntity {
    private String name;
    private ArrayList<String> annotate_exam;
    private ArrayList<String> emr_symptom;
    private ArrayList<String> annotate_symptom; //症状名称
    private ArrayList<String> emr_exam;
    private String level;
    private String type;
    private String desc; //描述
}
