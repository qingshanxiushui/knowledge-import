package com.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class positiveInspectResultDto { //阳性检查
    private String result; //结果
    private String title; //项目名
    private String desc; //证据内容
}
