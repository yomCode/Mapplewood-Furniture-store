package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.PickupCenterRequest;
import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.enums.State;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.PickupRepository;
import com.decagon.OakLandv1be.repositries.StateRepository;
import com.decagon.OakLandv1be.services.PickupService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PickupServiceImplTest {

    @Mock
    PickupRepository pickupRepository;

    @Mock
    StateRepository stateRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    SecurityContextHolder securityContextHolder;

    @InjectMocks
    private PickupServiceImpl pickupCenterService;

    PickupCenterResponse response;
    String pickUpCenterName;
    PickupCenter pickup;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        pickUpCenterName = "Roban Stores";
        pickup = new PickupCenter();
        pickup.setName(pickUpCenterName);
    }

    @Test
    public void testGetCenterByName() {

        when(pickupRepository.findByName(pickUpCenterName))
                .thenReturn(Optional.of(pickup));
        PickupCenterResponse response = pickupCenterService.getCenterByName(pickUpCenterName);

        assertEquals(pickUpCenterName, response.getName());
    }

    @Test
    public void testGetCenterByStateName() {
        String stateName = "Test State";
        PickupCenter pickup1 = new PickupCenter();
        PickupCenter pickup2 = new PickupCenter();

        when(pickupRepository.findAll()).thenReturn(Arrays.asList(pickup1, pickup2));

        List<PickupCenterResponse> responses = pickupCenterService.getCenterByStateName(stateName);

        assertEquals(1, responses.size());
        assertEquals(stateName, responses.get(0).getStateName());
    }

    @Test
    public void testDeleteCenter() {
        Long id = 1L;
        when(pickupRepository.existsById(id)).thenReturn(true);

        pickupCenterService.deleteCenter(id);

        verify(pickupRepository).deleteById(id);
    }

}