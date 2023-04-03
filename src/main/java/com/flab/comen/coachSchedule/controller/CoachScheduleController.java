package com.flab.comen.coachSchedule.controller;

import com.flab.comen.coachSchedule.dto.CoachScheduleInsertDto;
import com.flab.comen.coachSchedule.dto.UnmatchedScheduleDeleteDto;
import com.flab.comen.coachSchedule.service.CoachScheduleService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachScheduleController {

    CoachScheduleService coachScheduleService;

    public CoachScheduleController(CoachScheduleService coachScheduleService) {
        this.coachScheduleService = coachScheduleService;
    }

    @PostMapping
    public ResponseEntity<CoachScheduleInsertDto> insert(@RequestBody @Valid CoachScheduleInsertDto dto){
        return ResponseEntity.ok(coachScheduleService.insertSchedule(dto));
    }

//    일정조회 구현시 진행
//    @GetMapping
//    public ResponseEntity<?> select(@RequestBody @Valid ? dto){
//
//    }

//    일정변경 구현시 진행
//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody @Valid ? dto){
//
//    }

    @DeleteMapping
    public ResponseEntity<UnmatchedScheduleDeleteDto> unmatchedScheduleDelete(@RequestBody @Valid UnmatchedScheduleDeleteDto dto){
        coachScheduleService.unmatchedScheduleDelete(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    일정변경 구현시 진행
//    @DeleteMapping
//    public ResponseEntity<> matchedScheduleDelete(@RequestBody @NonNull long tid){
//
//    }

}
