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
public class AllData {
    private List<Record> records= new ArrayList<Record>(16);
    public void addRecord(Record  record) {
        records.add(record);
    }
}
