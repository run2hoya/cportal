package com.castis.cportal.dto.donation;

import com.castis.cportal.model.ViewDonation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationData {

    public List<ViewDonation> viewDonationList = new ArrayList<ViewDonation>();


}
