package com.csmtech.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ans_id")
	private Integer ansId;

	@Column(name = "candidate_id")
	private Integer candidate;

	@Column(name = "question_id")
	private Integer question;

	@Column(name = "opt_choose")
	private String optChoose;

	private String status;

	private Integer mark;

}
