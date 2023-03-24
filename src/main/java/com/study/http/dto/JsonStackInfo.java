package com.study.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonStackInfo {
    private String elementName;
    private String elementType;
    private Object objectValue;
}
