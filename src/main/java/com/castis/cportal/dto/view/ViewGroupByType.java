package com.castis.cportal.dto.view;

import com.castis.cportal.model.ViewTable;
import lombok.Data;

import java.util.List;

@Data
public class ViewGroupByType {

    List<ViewTable> platinum;
    List<ViewTable> premier;
    List<ViewTable> normal;
    List<ViewTable> etc;


    public ViewGroupByType(List<ViewTable> platinum, List<ViewTable> premier,
                           List<ViewTable> normal, List<ViewTable> etc) {
        this.platinum = platinum;
        this.premier = premier;
        this.normal = normal;
        this.etc = etc;
    }
}
