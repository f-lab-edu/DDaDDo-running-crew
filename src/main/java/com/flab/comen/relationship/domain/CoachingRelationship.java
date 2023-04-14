package com.flab.comen.relationship.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
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

	public static CoachingRelationship of(Long coachId, Long menteeId, boolean activeYn, LocalDate startedDt,
		LocalDate expiredDt) {
		CoachingRelationship coachingRelationship = new CoachingRelationship();
		coachingRelationship.coachId = coachId;
		coachingRelationship.menteeId = menteeId;
		coachingRelationship.activeYn = activeYn;
		coachingRelationship.startedDt = startedDt;
		coachingRelationship.expiredDt = expiredDt;
		return coachingRelationship;
	}
}
