package com.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositiveSymptomReusltDto { //阳性症状
    private String name; //名称
    private String desc; //内容
}
