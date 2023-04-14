package com.flab.comen.relationship.dto;

import java.time.LocalDate;

import com.flab.comen.relationship.domain.CoachingRelationship;

public record RelationshipResponse(
	Long tid,
	Long coachId,
	Long menteeId,
	boolean activeYn,
	LocalDate startedDt,
	LocalDate expiredDt
) {
	public static RelationshipResponse from(CoachingRelationship coachingRelationship) {
		return new RelationshipResponse(
			coachingRelationship.getTid(),
			coachingRelationship.getCoachId(),
			coachingRelationship.getMenteeId(),
			coachingRelationship.isActiveYn(),
			coachingRelationship.getStartedDt(),
			coachingRelationship.getExpiredDt()
		);
	}
}
