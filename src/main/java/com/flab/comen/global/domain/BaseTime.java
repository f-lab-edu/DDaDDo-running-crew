package com.flab.comen.global.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BaseTime {
	private LocalDateTime createdDt;

	private LocalDateTime updatedDt;
}
