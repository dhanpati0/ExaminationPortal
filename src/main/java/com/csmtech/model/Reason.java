package com.csmtech.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "reason")
public class Reason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reasonId;
	private String reason;
	private LocalDate date;
	private LocalTime time;
	private Integer candidateId;
}
