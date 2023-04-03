package com.flab.comen.coachSchedule.service;

import com.flab.comen.coachSchedule.dto.CoachScheduleInsertDto;
import com.flab.comen.coachSchedule.dto.UnmatchedScheduleDeleteDto;
import com.flab.comen.coachSchedule.mapper.CoachScheduleMapper;
import com.flab.comen.global.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.flab.comen.global.exception.ErrorMessage.DUPLICATE_SCHEDULE_INSERT;
import static com.flab.comen.global.exception.ErrorMessage.SCHEDULE_INSERT_DEADLINE_PASSED;
import static com.flab.comen.global.utils.DateUtils.isDeadlinePassed;

@Service
public class CoachScheduleService {

    CoachScheduleMapper coachScheduleMapper;

    public CoachScheduleService(CoachScheduleMapper coachScheduleMapper) {
        this.coachScheduleMapper = coachScheduleMapper;
    }

    public CoachScheduleInsertDto insertSchedule(CoachScheduleInsertDto dto) {
        LocalDateTime now = LocalDateTime.now();

        int todayWeekNum = DateUtils.getWeekOfYearFromToday(now);
        int requestWeekNum = dto.getWeekNumber();

        if (todayWeekNum >= requestWeekNum || isDeadlinePassed(now)) {
            throw new IllegalArgumentException(SCHEDULE_INSERT_DEADLINE_PASSED.getMessage());
        }

        int duplicateScheduleCnt = coachScheduleMapper.selectDuplicateSchedule(dto.getPossibleDt());
        if (duplicateScheduleCnt > 0) {
            throw new IllegalArgumentException(DUPLICATE_SCHEDULE_INSERT.getMessage());
        }

        coachScheduleMapper.insertSchedule(dto);
        return dto;
    }

    public void unmatchedScheduleDelete(UnmatchedScheduleDeleteDto dto){
        coachScheduleMapper.updateSchedule(dto);
    }

    public int updateMenteesCoachExpireDate(String nextMondayDate){
        return coachScheduleMapper.updateMenteesCoachExpireDate(nextMondayDate);
    }
}
