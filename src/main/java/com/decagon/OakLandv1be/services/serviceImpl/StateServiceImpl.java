package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;
import com.decagon.OakLandv1be.repositries.StateRepository;
import com.decagon.OakLandv1be.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    @Override
    public StateResponse getByName(String name) {
        return null;
    }

    @Override
    public StateResponse getById(Long id) {
        return null;
    }

    @Override
    public List<StateResponse> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public String create(StateRequest stateRequest) {
        return null;
    }
}
