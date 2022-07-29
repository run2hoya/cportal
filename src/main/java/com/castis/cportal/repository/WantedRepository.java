package com.castis.cportal.repository;

import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.wanted.WantedWithContentDto;
import com.castis.cportal.model.Wanted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WantedRepository extends JpaRepository<Wanted, Long> {

    public List<Wanted> findByRegisterIdAndRegistDateBetween(int registerId, LocalDateTime start, LocalDateTime end);

    @Query(value="SELECT new com.castis.cportal.dto.wanted.WantedWithContentDto"
            + "(wt.title, wt.wantedType, wt.productType, wt.bgImg, wt.startDate, wt.endDate, wt.id, wt.viewCnt, wt.candidateCnt,"
            + "wt.likeCnt, wt.jobType, u.userImg, u.nickName, wt.registDate, wt.email) "
            + "FROM Wanted wt "
            + "left outer join User u on wt.registerId= u.id "
            + "where wt.startDate <= :date and wt.endDate >= :date and "
            + "wt.productType = :productType and wt.wantedType = :wantedType and wt.open = true "
            + "order by wt.id desc" ,
            nativeQuery = false)
    public List<WantedWithContentDto> getWantedList(@Param("wantedType") String wantedType,
                                                    @Param("date") LocalDate date,
                                                    @Param("productType") ProductType productType);


}