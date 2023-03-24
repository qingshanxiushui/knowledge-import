package com.study.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicEmrPatientInfo {
    private String id;
    private String age;
    private String gender;
    private String birth_date;
}
