package com.flab.comen.coachSchedule.dto;

import jakarta.validation.constraints.NotBlank;

public class UnmatchedScheduleDeleteDto implements CoachScheduleUpdateDtoValidator {

    @NotBlank(message = "스케쥴 id가 누락되었습니다.")
    long tid;

    @NotBlank(message = "수정자 정보가 누락되었습니다.")
    String updatedBy;

}
