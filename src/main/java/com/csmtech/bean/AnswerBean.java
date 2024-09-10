package com.csmtech.bean;

import java.io.Serializable;


import lombok.Data;
@Data
public class AnswerBean implements Serializable {
	/**
	 * Auto-generated serialVersionUID.
	 */
	private static final long serialVersionUID = 7227916470136627857L;
	private Integer questionId;
	private String[] option;
	private String correctAns;
}
