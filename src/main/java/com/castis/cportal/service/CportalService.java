package com.castis.cportal.service;

import com.castis.cportal.dto.chart.ChartDataDto;
import com.castis.cportal.dto.cportal.StatDto;
import com.castis.cportal.repository.CompanyRepository;
import com.castis.cportal.repository.UserRepository;
import com.castis.cportal.repository.WantedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CportalService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final WantedRepository wantedRepository;

    public StatDto getStatDto() {

        LocalDate today = LocalDate.now();
        Long jobCnt = wantedRepository.countByOpenAndWantedTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                true,"일자리", today, today);
        Long workCnt = wantedRepository.countByOpenAndWantedTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                true,"일거리", today, today);;
        Long albaCnt = wantedRepository.countByOpenAndWantedTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                true,"알바", today, today);;
        Long sellCompanyCnt = wantedRepository.countByOpenAndWantedTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                true,"사업부 매각", today, today);;

        long companyCnt = companyRepository.count();
        long userCnt = userRepository.count();


        return new StatDto(companyCnt, userCnt, jobCnt, workCnt, albaCnt, sellCompanyCnt);
    }

    public List<ChartDataDto> getJobcastGroupChart() {
        return wantedRepository.getWantedGroupData(LocalDate.now());
    }



}
