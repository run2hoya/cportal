package com.castis.cportal.service;

import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.enumeration.BookingState;
import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.view.ViewData;
import com.castis.cportal.dto.view.ViewGroupByType;
import com.castis.cportal.dto.view.ViewItem;
import com.castis.cportal.dto.view.ViewResponse;
import com.castis.cportal.model.View;
import com.castis.cportal.model.ViewSetting;
import com.castis.cportal.model.ViewTable;
import com.castis.cportal.repository.ViewRepository;
import com.castis.cportal.repository.ViewSettingRepository;
import com.castis.cportal.repository.ViewTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewService {

    private final ViewRepository viewRepository;
    private final ViewTableRepository viewTableRepository;
    private final ViewSettingRepository viewSettingRepository;

    public ViewTable getViewTable(Long viewTableId) {
        return viewTableRepository.findOne(viewTableId);
    }

    public ViewSetting getViewSetting(Long viewTableId) {
        return viewSettingRepository.findOneByViewTableId(viewTableId);
    }
    @Transactional
    public boolean updateViewSetting(ViewSetting viewSetting) {

        ViewSetting currentView;
        if(viewSetting.getId() != null) {
            currentView  = viewSettingRepository.findOne(viewSetting.getId());
        } else {
            currentView = new ViewSetting();
            currentView.setViewTableId(viewSetting.getViewTableId());
        }

        currentView.setAccount(viewSetting.getAccount());
        currentView.setMemo(viewSetting.getMemo());
        currentView.setMaxMonth(viewSetting.getMaxMonth());
        viewSettingRepository.save(currentView);
        return true;
    }


    public ViewGroupByType getViewTitleDto() {

        List<ViewTable> platinum = viewTableRepository.findByProductType(ProductType.PLATINUM);
        List<ViewTable> premier = viewTableRepository.findByProductType(ProductType.PREMIER);
        List<ViewTable> normal = viewTableRepository.findByProductType(ProductType.NORMAL);
        List<ViewTable> etc = viewTableRepository.findByProductType(ProductType.ETC);

        return new ViewGroupByType(platinum, premier, normal, etc);
    }

    public ViewData getViewList(TransactionID trId, long viewTableId, LocalDate start, LocalDate end) {
        List<View> viewList = viewRepository.findByViewTableIdAndViewDateBetween(viewTableId, start, end);

        ViewData viewData = new ViewData();
        StringBuilder sb = new StringBuilder();
        sb.append("<th>비춰보기</th>");
        LocalDate date;
        for(date = start; date.isBefore(end); date = date.plusDays(1)) {
            sb.append("<th>" + date.format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN)) + "</th>");
        }
        sb.append("<th>" + date.format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN)) + "</th>");

        Map<String,List<View>> map = new LinkedHashMap<>();
        for(View view : viewList) {
            map.computeIfAbsent(view.getTimezone(), k -> new ArrayList<View>());
            map.get(view.getTimezone()).add(view);
        }

        for( Map.Entry<String, List<View>> elem : map.entrySet() ){
            viewData.getViewItemList().add(new ViewItem(elem.getKey(), elem.getValue()));
        }
        viewData.setTh(sb.toString());
        return viewData;
    }

    public void setStrDate(LocalDate date, StringBuilder sb) {
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
            sb.append("<th style=\"text-color:red\">" + date.format(DateTimeFormatter.ofPattern("MM월 dd일(E)")) + "</th>");
        else
            sb.append("<th>" + date.format(DateTimeFormatter.ofPattern("MM월 dd일(E)")) + "</th>");
    }

    public List<View> getViewItem(String startDate, String endDate, long viewTableId) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return viewRepository.findByViewTableIdAndViewDateBetween(viewTableId, start, end);
    }

    public void createViewMonth(String startDate, long viewTableId) {

        View view;
        LocalTime startTime = LocalTime.parse("09:00", DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse("22:00", DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = start.plusMonths(1);

        for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            view = new View(viewTableId, date, "대한민국 시간");
            viewRepository.save(view);
        }

        for(LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(30)) {

            for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                view = new View(viewTableId, date, time.format(DateTimeFormatter.ofPattern("HH:mm")));
                viewRepository.save(view);
            }
        }

        for(int i=1; i <6; i++) {
            for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                view = new View(viewTableId, date, "예약 대기 " + i);
                viewRepository.save(view);
            }
        }

    }


    public View saveView(View view) {
        return viewRepository.save(view);
    }

    @Transactional
    public boolean updateView(View currentView, View view, boolean isOwner, int userId) {

        if(!isOwner && currentView.getRegisterId() != null) {
            if(currentView.getRegisterId() != view.getRegisterId())
                return false;
        }

        if(currentView.getRegisterId() != null && currentView.getRegisterId() != userId && isOwner) {
            currentView.setTitle("<b>" + view.getTitle() + "</b><hr>" + currentView.getTitle());
            currentView.setBookingState(BookingState.CONFLICT);
        } else {
            currentView.setTitle(view.getTitle());
            currentView.setBookingInfo(view.getBookingInfo());
            currentView.setIsOnline(view.getIsOnline());
            currentView.setRegisterId(view.getRegisterId());
            currentView.setBookingState(BookingState.BOOKING);
            currentView.setRegisterMember(view.getRegisterMember());
        }
        viewRepository.save(currentView);
        return true;
    }

    public List<ViewResponse> updateViewItemList(TransactionID trId, ViewItem viewItem, int userId) {
        List<ViewResponse> responseList = new ArrayList<ViewResponse>();
        if(viewItem != null && viewItem.getViewList() != null && !viewItem.getViewList().isEmpty()) {

            ViewTable viewTable = getViewTable(viewItem.getViewTableId());
            boolean isOwner = (userId == viewTable.getOwnerId());
            for(View view : viewItem.getViewList()) {

                View currentView;
                if(view.getId() == null) {
                    currentView = saveView(new View(viewItem.getViewTableId(), view.getViewDate(), view.getTimezone()));
                } else
                    currentView = viewRepository.findOne(view.getId());
                try {
                    boolean result = updateView(currentView, view, isOwner, userId);
                    responseList.add(new ViewResponse(
                            currentView.getViewDate().format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN))
                                    + "_" + currentView.getTimezone(),
                            result));
                } catch (Exception e) {
                    log.error(trId + "" , e);
                    responseList.add(new ViewResponse(
                            currentView.getViewDate().format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN))
                                    + "_" + currentView.getTimezone(),
                            false));
                }
            }

            return responseList;
        }
        return null;

    }

    @Transactional
    public boolean deleteView(View currentView, boolean isOwner, int userId) {

        if(!isOwner && currentView.getRegisterId() != null) {
            if(currentView.getRegisterId() != userId)
                return false;
        }

        viewRepository.delete(currentView.getId());
        return true;
    }

    public List<ViewResponse> deleteViewItemList(TransactionID trId, ViewItem viewItem, int userId) {
        List<ViewResponse> responseList = new ArrayList<ViewResponse>();
        if(viewItem != null && viewItem.getViewList() != null && !viewItem.getViewList().isEmpty()) {

            ViewTable viewTable = getViewTable(viewItem.getViewTableId());
            boolean isOwner = (userId == viewTable.getOwnerId());
            for(View view : viewItem.getViewList()) {

                View currentView = viewRepository.findOne(view.getId());
                try {

                    boolean result = deleteView(currentView, isOwner, userId);
                    responseList.add(new ViewResponse(
                            currentView.getViewDate().format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN))
                                    + "_" + currentView.getTimezone(),
                            result, currentView.getId()));
                } catch (Exception e) {
                    log.error(trId + "" , e);
                    responseList.add(new ViewResponse(
                            currentView.getViewDate().format(DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN))
                                    + "_" + currentView.getTimezone(),
                            false, currentView.getId()));
                }
            }

            return responseList;
        }
        return null;

    }


}
