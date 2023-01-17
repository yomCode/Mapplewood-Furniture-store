package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.PickupCenterRequest;
import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PickupService {
    PickupCenterResponse getCenterByName(String name);

    List<PickupCenterResponse> getCenterByStateName(String name);

    void deleteCenter(Long id);

    String createCenter(PickupCenterRequest pickupCenterRequest);

    Page<PickupCenterResponse> getAll(int page, int size);
}
