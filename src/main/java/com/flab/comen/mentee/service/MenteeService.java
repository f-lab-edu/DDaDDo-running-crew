package com.flab.comen.mentee.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.flab.comen.relationship.domain.CoachingRelationship;
import com.flab.comen.relationship.dto.RelationshipResponse;
import com.flab.comen.relationship.mapper.CoachingRelationshipMapper;
import com.flab.comen.schedule.domain.CoachingSchedule;
import com.flab.comen.schedule.dto.ScheduleResponse;
import com.flab.comen.schedule.mapper.CoachingScheduleMapper;

@Service
public class MenteeService {

	private final CoachingScheduleMapper coachingScheduleMapper;
	private final CoachingRelationshipMapper coachingRelationshipMapper;

	public MenteeService(CoachingScheduleMapper coachingScheduleMapper,
		CoachingRelationshipMapper coachingRelationshipMapper) {
		this.coachingScheduleMapper = coachingScheduleMapper;
		this.coachingRelationshipMapper = coachingRelationshipMapper;
	}

	public Optional<ScheduleResponse> getSchedule(Long memberId, LocalDate date) {
		CoachingSchedule schedule = coachingScheduleMapper.findByMenteeIdAndPossibleDt(memberId, date);

		return Optional.ofNullable(schedule)
			.map(ScheduleResponse::from);
	}

	public Optional<RelationshipResponse> getRelationship(Long memberId, boolean activeYn) {
		CoachingRelationship relationship = coachingRelationshipMapper.findByMenteeIdAndActiveYn(memberId, activeYn);

		return Optional.ofNullable(relationship)
			.map(RelationshipResponse::from);
	}
}
