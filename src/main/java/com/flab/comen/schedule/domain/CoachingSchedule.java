package com.flab.comen.schedule.domain;

import java.time.LocalDateTime;

import com.flab.comen.global.domain.BaseTime;

import lombok.AccessLevel;
import lombok.Builder;
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

	@Builder
	private CoachingSchedule(Long coachId, Long menteeId, int weekNumber, boolean cancelYn,
		String feedback, LocalDateTime feedbackCreatedDt, LocalDateTime possibleDt, Long updatedBy) {
		this.coachId = coachId;
		this.menteeId = menteeId;
		this.weekNumber = weekNumber;
		this.cancelYn = cancelYn;
		this.feedback = feedback;
		this.feedbackCreatedDt = feedbackCreatedDt;
		this.possibleDt = possibleDt;
		this.updatedBy = updatedBy;
	}

	public static CoachingSchedule of(Long coachId, Long menteeId, int weekNumber, boolean cancelYn,
		String feedback, LocalDateTime feedbackCreatedDt, LocalDateTime possibleDt, Long updatedBy) {
		return CoachingSchedule.builder()
			.coachId(coachId)
			.menteeId(menteeId)
			.weekNumber(weekNumber)
			.cancelYn(cancelYn)
			.feedback(feedback)
			.feedbackCreatedDt(feedbackCreatedDt)
			.possibleDt(possibleDt)
			.updatedBy(updatedBy)
			.build();
	}
}
