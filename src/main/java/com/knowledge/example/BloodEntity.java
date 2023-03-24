package com.knowledge.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodEntity {
    private boolean isCurrent;
    private String name;
}
