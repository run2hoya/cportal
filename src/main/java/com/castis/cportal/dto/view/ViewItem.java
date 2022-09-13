package com.castis.cportal.dto.view;

import com.castis.cportal.model.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewItem {

    String title;
    List<View> viewList;
}
