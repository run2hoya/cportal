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

    public String th;
    public List<ViewItem> viewItemList = new ArrayList<ViewItem>();
    public boolean isOwner;
    public String viewTitle;

    public ViewData(String th, List<ViewItem> viewItemList) {
        this.th = th;
        this.viewItemList = viewItemList;
    }
}
