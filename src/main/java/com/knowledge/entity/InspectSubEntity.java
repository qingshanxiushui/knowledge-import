package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class InspectSubEntity {
    private String level;
    private ArrayList<InspectSubItemEntity> items;
}
