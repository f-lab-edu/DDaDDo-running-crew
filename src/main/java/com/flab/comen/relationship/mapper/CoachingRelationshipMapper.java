package com.flab.comen.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flab.comen.relationship.domain.CoachingRelationship;

@Mapper
public interface CoachingRelationshipMapper {
	CoachingRelationship findByMenteeIdAndActiveYn(@Param("menteeId") Long menteeId,
		@Param("activeYn") boolean activeYn);
}
