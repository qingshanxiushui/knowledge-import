package com.study.http.dto;

import com.alibaba.fastjson.JSON;
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
public class Record {
    private List<Column> columns= new ArrayList<Column>(16);
    public void addColumn(Column column) {
        columns.add(column);
    }
    public int getColumnSize(){return columns.size();}

}
