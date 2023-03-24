package com.study.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicEmrDto {
    private String record_id;
    private String record_type;
    private String medical_id;
    private String visit_id;
    private Object patient_info;
    private String record_time;
    private String admission_time;
    private String hospital;
    private String dept;
    private String apply_dept;
    private String exam_name;
    private String exam_method;
    private String sample_category;
    private String sample_status;
    private String sample_id;
    private String sample_time;
    private String receive_time;
    private String exam_time;
    private String report_time;
    private List<Object> result_info = new ArrayList<Object>();

    public void addResultInfo(ClinicEmrResultInfo clinicEmrResultInfo) {
        result_info.add(clinicEmrResultInfo);
    };
}
