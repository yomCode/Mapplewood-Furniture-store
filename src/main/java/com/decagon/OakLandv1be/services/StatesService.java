package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;
import org.springframework.stereotype.Service;


public interface StatesService {
    String createState(StateRequest stateRequest);

    void deleteState(Long id);

    StateResponse viewStateByName(String name);
}

