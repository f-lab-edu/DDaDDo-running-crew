package com.flab.comen.coachSchedule.mapper;

import com.flab.comen.coachSchedule.dto.CoachScheduleInsertDto;
import com.flab.comen.coachSchedule.dto.CoachScheduleUpdateDtoValidator;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoachScheduleMapper {

    int insertSchedule(CoachScheduleInsertDto dto);

    int selectDuplicateSchedule(String possibleDt);

    <T extends CoachScheduleUpdateDtoValidator> int updateSchedule(T dto);

    int updateMenteesCoachExpireDate(String nextMondayDate);
}
