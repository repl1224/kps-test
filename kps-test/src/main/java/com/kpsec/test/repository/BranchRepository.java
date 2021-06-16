package com.kpsec.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpsec.test.model.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
	
}
