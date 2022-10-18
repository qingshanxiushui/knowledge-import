package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class InspectEntity {
    private String name;
    private ArrayList<String> subject;
    private ArrayList<InspectSubEntity> 检查;
}
