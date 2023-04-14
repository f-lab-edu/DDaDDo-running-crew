package com.flab.comen.schedule.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flab.comen.schedule.domain.CoachingSchedule;

@Mapper
public interface CoachingScheduleMapper {
	CoachingSchedule findByMenteeIdAndPossibleDt(@Param("menteeId") Long menteeId, @Param("date") LocalDate date);
}
