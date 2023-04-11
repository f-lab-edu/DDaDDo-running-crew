package com.flab.comen.relationship.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachingRelationship {
	private Long tid;
	private Long coachId;
	private Long menteeId;
	private boolean activeYn;
	private LocalDate startedDt;
	private LocalDate expiredDt;

	@Builder
	private CoachingRelationship(Long coachId, Long menteeId, boolean activeYn,
		LocalDate startedDt, LocalDate expiredDt) {
		this.coachId = coachId;
		this.menteeId = menteeId;
		this.activeYn = activeYn;
		this.startedDt = startedDt;
		this.expiredDt = expiredDt;
	}

	public static CoachingRelationship of(Long coachId, Long menteeId, boolean activeYn, LocalDate startedDt,
		LocalDate expiredDt) {
		return CoachingRelationship.builder()
			.coachId(coachId)
			.menteeId(menteeId)
			.activeYn(activeYn)
			.startedDt(startedDt)
			.expiredDt(expiredDt)
			.build();
	}
}
