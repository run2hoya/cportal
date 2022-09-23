package com.castis.cportal.service;

import com.castis.cportal.dto.donation.DonationData;
import com.castis.cportal.model.ViewDonation;
import com.castis.cportal.repository.ViewDonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewDonationService {

    private final ViewDonationRepository viewDonationRepository;

    public List<ViewDonation> findByOwnerId(Integer ownerId) {
        return viewDonationRepository.findByOwnerId(ownerId);
    }

    public List<ViewDonation> getViewDonationByTableId(Long viewTableId, String start, String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(start, formatter), LocalDateTime.MIN.toLocalTime());
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(end, formatter), LocalDateTime.MAX.toLocalTime());

        return viewDonationRepository.findByViewTableIdAndPublishDateBetweenOrderByPublishDateDesc(viewTableId, startDateTime, endDateTime);
    }

    public List<ViewDonation> getViewDonation(Integer ownerId, String start, String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(start, formatter), LocalDateTime.MIN.toLocalTime());
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(end, formatter), LocalDateTime.MAX.toLocalTime());

        return viewDonationRepository.findByOwnerIdAndPublishDateBetweenOrderByPublishDateDesc(ownerId, startDateTime, endDateTime);
    }

    public void save(ViewDonation donation) {
        viewDonationRepository.save(donation);
    }

    public void saveDonationData(DonationData donationData) {

        for(ViewDonation donation : donationData.getViewDonationList()) {
            save(donation);
        }
    }

}
