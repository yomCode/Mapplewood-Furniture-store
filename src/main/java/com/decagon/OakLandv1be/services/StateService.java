package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;

import java.util.List;

public interface StateService {
    StateResponse getByName(String name);

    StateResponse getById(Long id);

    List<StateResponse> getAll();

    void delete(Long id);

    String create(StateRequest stateRequest);
}
