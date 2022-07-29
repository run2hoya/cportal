package com.castis.cportal.dto.wanted;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WantedApplicantDto implements Serializable {

    private String name;
    private String description;
    private String email;
    private String phone;
    private String companyEmail;
    private String wantedTitle;
    private Long wantedId;
}
