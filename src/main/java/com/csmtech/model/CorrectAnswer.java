package com.csmtech.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "correctans")
public class CorrectAnswer implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ansid")
	private Integer ansId;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="question_id")
	private Question questionId;
	
	@Column(name = "cor_ans")
	private String correctAns;
	
	
}
