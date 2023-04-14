package com.flab.comen.schedule.domain;

import java.time.LocalDateTime;

import com.flab.comen.global.domain.BaseTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachingSchedule extends BaseTime {
	private Long tid;
	private Long coachId;
	private Long menteeId;
	private int weekNumber;
	private boolean cancelYn;
	private String feedback;
	private LocalDateTime feedbackCreatedDt;
	private LocalDateTime possibleDt;
	private Long updatedBy;

	public static CoachingSchedule of(Long coachId, Long menteeId, int weekNumber, boolean cancelYn,
		String feedback, LocalDateTime feedbackCreatedDt, LocalDateTime possibleDt, Long updatedBy) {
		CoachingSchedule coachingSchedule = new CoachingSchedule();
		coachingSchedule.coachId = coachId;
		coachingSchedule.menteeId = menteeId;
		coachingSchedule.weekNumber = weekNumber;
		coachingSchedule.cancelYn = cancelYn;
		coachingSchedule.feedback = feedback;
		coachingSchedule.feedbackCreatedDt = feedbackCreatedDt;
		coachingSchedule.possibleDt = possibleDt;
		coachingSchedule.updatedBy = updatedBy;
		return coachingSchedule;
	}
}
