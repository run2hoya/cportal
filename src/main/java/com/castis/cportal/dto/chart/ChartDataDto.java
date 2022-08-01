package com.castis.cportal.dto.chart;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChartDataDto implements Serializable {
    private String name;
    private Long value;

    public ChartDataDto(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
