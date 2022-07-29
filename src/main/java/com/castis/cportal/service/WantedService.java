package com.castis.cportal.service;

import com.castis.commonLib.util.ObjectMapperUtils;
import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.wanted.WantedContentDto;
import com.castis.cportal.dto.wanted.WantedRegisterDto;
import com.castis.cportal.dto.wanted.WantedWithContentDto;
import com.castis.cportal.model.User;
import com.castis.cportal.model.Wanted;
import com.castis.cportal.repository.WantedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WantedService {

    private final WantedRepository wantedRepository;
    private final UserService userService;


    public void saveWanted(Wanted wanted) {
        wantedRepository.save(wanted);
    }

    public void saveWanted(WantedRegisterDto wantedRegisterDto) {
        Wanted wanted = ObjectMapperUtils.map(wantedRegisterDto, Wanted.class);
        wantedRepository.save(wanted);
    }

    public WantedRegisterDto getWantedInfo(long wantedId) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        if (wanted != null)
            return ObjectMapperUtils.map(wanted, WantedRegisterDto.class);
        else return null;
    }

    public List<WantedWithContentDto> getWanted(String wantedType, String date, ProductType productType) {
        LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return wantedRepository.getWantedList(wantedType, targetDate, productType);
    }

    @Transactional
    public WantedWithContentDto getWantedWithContent(long wantedId) {
        WantedWithContentDto wantedWithContentDto;
        Wanted wanted = wantedRepository.findOne(wantedId);
        if (wanted != null) {
            wanted.setViewCnt(wanted.getViewCnt() + 1);
            wantedRepository.save(wanted);
            wantedWithContentDto = ObjectMapperUtils.map(wanted, WantedWithContentDto.class);
        }
        else return null;

        User user = userService.getUser(wantedWithContentDto.getRegisterId());
        wantedWithContentDto.setNickName(user.getNickName());
        wantedWithContentDto.setUserImg(user.getUserImg());

        return wantedWithContentDto;
    }

    @Transactional
    public void incrementApplicant(long wantedId) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        wanted.setCandidateCnt(wanted.getCandidateCnt() + 1);
        wantedRepository.save(wanted);
    }

    @Transactional
    public void updateWantedWithContent(long wantedId, WantedWithContentDto wantedWithContentDto) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        wanted.setJobType(wantedWithContentDto.getJobType());
        wanted.setBgImg(wantedWithContentDto.getBgImg());
        wanted.setOpen(wantedWithContentDto.getOpen());
        wanted.setContent(wantedWithContentDto.getContent());
        wanted.setEmail(wantedWithContentDto.getEmail());
        wantedRepository.save(wanted);
    }

    @Transactional
    public void updateWanted(long wantedId, WantedRegisterDto wantedRegisterDto) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        wanted.setJobType(wantedRegisterDto.getJobType());
        wanted.setBgImg(wantedRegisterDto.getBgImg());
        wanted.setOpen(wantedRegisterDto.getOpen());
        wantedRepository.save(wanted);
    }

    public WantedContentDto getWantedContentDto(long wantedId) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        if (wanted != null)
            return ObjectMapperUtils.map(wanted, WantedContentDto.class);
        else return null;
    }

    @Transactional
    public void updateWantedContent(long wantedId, WantedContentDto wantedContentDto) {
        Wanted wanted = wantedRepository.findOne(wantedId);
        wanted.setContent(wantedContentDto.getContent());
        wantedRepository.save(wanted);
    }

    public List<WantedRegisterDto> getWantedInfo(int registerId, String start, String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(start, formatter), LocalDateTime.MIN.toLocalTime());
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(end, formatter), LocalDateTime.MAX.toLocalTime());
        List<Wanted> wantedList = wantedRepository.findByRegisterIdAndRegistDateBetween(registerId, startDateTime, endDateTime);
        if(wantedList != null && wantedList.size() > 0) {
            return ObjectMapperUtils.mapAll(wantedList, WantedRegisterDto.class);
        } else
            return new ArrayList<WantedRegisterDto>();
    }




}
