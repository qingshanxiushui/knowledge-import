package com.knowledge.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScaleEntity {

    private String disease;
    private ArrayList<ScaleContentEntity> list;
}
