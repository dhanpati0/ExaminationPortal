package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.model.Reason;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Integer> {

	Reason findByCandidateId(Integer id);

}
