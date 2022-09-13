package com.castis.cportal.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewData {

    String th;
    List<ViewItem> viewItemList = new ArrayList<ViewItem>();
    boolean isOwner;

    public ViewData(String th, List<ViewItem> viewItemList) {
        this.th = th;
        this.viewItemList = viewItemList;
    }
}
