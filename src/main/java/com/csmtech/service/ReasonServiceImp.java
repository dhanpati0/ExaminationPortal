package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.model.Reason;
import com.csmtech.repository.ReasonRepository;

@Service
public class ReasonServiceImp implements ReasonService {

	@Autowired
	private ReasonRepository reasonRepository;

	@Override
	public Reason saveReason(Reason reason) {
		return reasonRepository.save(reason);
	}

	@Override
	public Reason findByCandidateId(Integer id) {
		return reasonRepository.findByCandidateId(id);
	}

	@Override
	public List<Reason> findAll() {
		return reasonRepository.findAll();
	}

	}

