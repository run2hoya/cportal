package com.castis.cportal.repository;

import com.castis.cportal.model.ViewDonation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewDonationRepository extends JpaRepository<ViewDonation, Long> {

    List<ViewDonation> findByOwnerId(Integer ownerId);

    List<ViewDonation> findByViewTableId(Long viewTableId);

    List<ViewDonation> findByOwnerIdAndPublishDateBetweenOrderByPublishDateDesc(int ownerId, LocalDateTime start, LocalDateTime end);
    List<ViewDonation> findByViewTableIdAndPublishDateBetweenOrderByPublishDateDesc(long viewTableId, LocalDateTime start, LocalDateTime end);

}