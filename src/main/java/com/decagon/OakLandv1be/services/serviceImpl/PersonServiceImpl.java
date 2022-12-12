package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

}
