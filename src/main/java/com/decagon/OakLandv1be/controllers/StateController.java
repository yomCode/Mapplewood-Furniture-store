package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;
import com.decagon.OakLandv1be.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/state")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody StateRequest stateRequest){
        return new ResponseEntity<>(stateService.create(stateRequest),HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<StateResponse> getByName(@PathVariable String name){
        return ResponseEntity.ok(stateService.getByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(stateService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getAll(){
        return ResponseEntity.ok(stateService.getAll());
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void deleteState(@PathVariable Long id){
        stateService.delete(id);
    }

}
