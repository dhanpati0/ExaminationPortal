package com.csmtech.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class MyDto {
	
	private Integer candid;
	private LocalTime candStarTime; //SS
	private LocalTime candEndTime;

}
