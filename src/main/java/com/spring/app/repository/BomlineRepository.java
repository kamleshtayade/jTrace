package com.spring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.app.domain.Bomheader;
import com.spring.app.domain.Bomline;

/**
 * Spring Data JPA repository for the Bomline entity.
 */
public interface BomlineRepository extends JpaRepository<Bomline,Long> {
	
	List <Bomline> findByBomheader(Bomheader bomheader);

}
