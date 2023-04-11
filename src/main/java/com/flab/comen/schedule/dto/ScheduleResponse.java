package com.flab.comen.schedule.dto;

import java.time.LocalDateTime;

import com.flab.comen.schedule.domain.CoachingSchedule;

public record ScheduleResponse(
	Long tid,
	Long coachId,
	Long menteeId,
	int weekNumber,
	boolean cancelYn,
	String feedback,
	LocalDateTime feedbackCreatedDt,
	LocalDateTime possibleDt,
	LocalDateTime createdDt,
	LocalDateTime updatedDt,
	Long updatedBy
) {
	public static ScheduleResponse from(CoachingSchedule coachingSchedule) {
		return new ScheduleResponse(
			coachingSchedule.getTid(),
			coachingSchedule.getCoachId(),
			coachingSchedule.getMenteeId(),
			coachingSchedule.getWeekNumber(),
			coachingSchedule.isCancelYn(),
			coachingSchedule.getFeedback(),
			coachingSchedule.getFeedbackCreatedDt(),
			coachingSchedule.getPossibleDt(),
			coachingSchedule.getCreatedDt(),
			coachingSchedule.getUpdatedDt(),
			coachingSchedule.getUpdatedBy()
		);
	}

}
