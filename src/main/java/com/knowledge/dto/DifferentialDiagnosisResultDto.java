package com.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifferentialDiagnosisResultDto { //鉴别诊断
    private String name; //名称
    private String performance; //内容
}
