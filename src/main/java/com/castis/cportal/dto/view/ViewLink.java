package com.castis.cportal.dto.view;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewLink {

    public String title;
    public String link;
    public Integer ownerId;
    public ProductType productType;

}
