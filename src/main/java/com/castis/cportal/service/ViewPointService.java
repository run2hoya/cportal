package com.castis.cportal.service;

import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.model.ViewPoint;
import com.castis.cportal.repository.ViewPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewPointService {

    private final ViewPointRepository viewPointRepository;

    public List<ViewPoint> findByViewTableId(Long viewTableId) {
        return viewPointRepository.findByViewTableId(viewTableId);
    }

    @Transactional
    public void updateViewPoint(TransactionID trId, ViewPoint viewPoint) {
        ViewPoint currentViewPoint = viewPointRepository.findOne(viewPoint.getId());
        log.info(trId+ currentViewPoint.toString());

        currentViewPoint.setCurrentPoint(viewPoint.getCurrentPoint());
        currentViewPoint.setAccumulatePoint(viewPoint.getAccumulatePoint());
        currentViewPoint.setUsePoint(viewPoint.getUsePoint());
        viewPointRepository.save(currentViewPoint);
    }


}
