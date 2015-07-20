package com.spring.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.app.domain.Bomheader;
import com.spring.app.domain.Bomline;

/**
 * Spring Data JPA repository for the Bomline entity.
 */
public interface BomlineRepository extends JpaRepository<Bomline,Long> {
	
	List <Bomline> findByBomheader(Bomheader bomheader);
	
	// Bomline containing Itemmtr - invert whereused
	@Query("select u from Bomline u where u.bomheader.itemmtr.id = :id")
	Page<Bomline> findByItemmtrId(@Param("id") Long id,Pageable pageable);
}
