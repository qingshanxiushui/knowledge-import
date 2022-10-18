package com.knowledge.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmahaEntity {
    @Excel(name = "entityId")
    private String entityId;
    @Excel(name = "entity")
    private String entity;
    @Excel(name = "entityTag")
    private String entityTag;
    @Excel(name = "property")
    private String property;
    @Excel(name = "valueId")
    private String valueId;
    @Excel(name = "value")
    private String value;
    @Excel(name = "valueTag")
    private String valueTag;
    @Excel(name = "group")
    private String group;
    @Excel(name = "source")
    private String source;
}
