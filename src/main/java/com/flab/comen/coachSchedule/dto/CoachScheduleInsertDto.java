package com.flab.comen.coachSchedule.dto;

import com.flab.comen.global.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CoachScheduleInsertDto {

    long tid;
    @NotBlank(message = "coach id가 누락되었습니다.")
    long coachId;
    @NotBlank(message = "코치 일정이 누락되었습니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
            message = "날짜 형식이 잘못되었습니다. (올바른 형식 : yyyy-MM-dd HH:mm:ss)")
    String possibleDt;
    Integer weekNumber;

    public CoachScheduleInsertDto(long coachId, String possibleDt) {
        this.coachId = coachId;
        this.possibleDt = possibleDt;
        this.weekNumber = DateUtils.calculateWeekOfYear(possibleDt);
    }

}
