package com.csmtech.model;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "candidate")
@Entity
public class Candidate {
	
	public Candidate(Candidate source, LocalTime newStartTime) {
        this.candid = source.candid;
        this.candFirstname = source.candFirstname;
        this.candLastname = source.candLastname;
        this.candpassword = source.candpassword;
        this.candidateemail = source.candidateemail;
        this.candMobile = source.candMobile;
        this.candCollegeName = source.candCollegeName;
        this.status = source.status;
        this.isdelete = source.isdelete;
        this.subTestTaker = source.subTestTaker;
        this.markAppear = source.markAppear;
        this.totalMark = source.totalMark;
        this.resultStatus = source.resultStatus;
        this.candLoginTime = source.candLoginTime;
        this.candStartTime = newStartTime; 
        this.candEndTime = source.candEndTime;
        this.progress = source.progress;
        this.pause = source.pause;
        this.reasonForLogOut = source.reasonForLogOut;
    }

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "candidate_id")
	private Integer candid;

	@Column(name = "candidate_first_name")
	private String candFirstname;

	@Column(name = "candidate_last_name")
	private String candLastname;

	@Column(name = "candidate_password")
	private String candpassword;

	@Column(name = "candidate_email")
	private String candidateemail;

	@Column(name = "candidate_mobile_no")
	private String candMobile;

	@Column(name = "candidate_college_name")
	private String candCollegeName;

	private String status;

	@Column(name = "is_delete")
	private String isdelete;
    
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subtest_taker_id")
	private SubTestTaker subTestTaker;

	@Column(name = "mark_appear")
	private Integer markAppear;

	@Column(name = "total_mark")
	private Double totalMark;

	@Column(name = "result_status")
	private String resultStatus;
	
	@Column(name="candlogin_time")
	private LocalTime candLoginTime;
	
	
	@Column(name="candstart_time")
	private LocalTime candStartTime;
	
	@Column(name="candend_time")
	private LocalTime candEndTime;
	
//SS
	@Column(name = "progress")
	private String progress;
	
	private String pause;
	
	private String reasonForLogOut;
	
	@OneToOne
    @JoinColumn(name = "configure_id")
    private Configure configure;

}
