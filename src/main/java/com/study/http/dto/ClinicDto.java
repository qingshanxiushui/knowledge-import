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
public class ClinicDto {
    private String action;
    private List<String> type = new ArrayList<String>();
    private List<Object> params = new ArrayList<Object>();
    private List<Object> emr_contents = new ArrayList<Object>();

    public void addEmr(ClinicEmrDto clinicEmrDto) {
        emr_contents.add(clinicEmrDto);
    }
}
