package com.study.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicEmrResultInfo {
    private String item_name;
    private String item_abbr;
    private String item_result;
    private String item_unit;
    private String item_hint;
}
