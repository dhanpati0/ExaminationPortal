package com.csmtech.service;

import java.util.List;

import com.csmtech.model.Reason;


public interface ReasonService {

	Reason saveReason(Reason reason);

	Reason findByCandidateId(Integer id);

	List<Reason> findAll();

	
	
}
