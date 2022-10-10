package com.castis.cportal.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewResponse {
    String targetDate;
    Boolean success;
    String description;

    Long viewId;

    public ViewResponse(String targetDate, Boolean success) {
        this.targetDate = targetDate;
        this.success = success;
    }

    public ViewResponse(String targetDate, Boolean success, Long viewId) {
        this.targetDate = targetDate;
        this.success = success;
        this.viewId = viewId;
    }
}
