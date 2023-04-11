package com.flab.comen.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.flab.comen.relationship.domain.CoachingRelationship;

@Mapper
public interface CoachingRelationshipMapper {
	CoachingRelationship findByMenteeIdAndActiveYn(Long menteeId);
}
