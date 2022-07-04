package com.castis.cportal.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyByType {

    String[] labels;
    int[] series;

    public CompanyByType(int cnt) {
        this.labels = new String[cnt];
        this.series = new int[cnt];
    }
}
