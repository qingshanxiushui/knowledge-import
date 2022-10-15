package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DiagnosticFactorsEntity {
    private String disease;
    private ArrayList<String> symptom_annotate_list;
    private ArrayList<String> exam_annotate_list;
    private ArrayList<DiagnosticFactorEntity> factor;
}
