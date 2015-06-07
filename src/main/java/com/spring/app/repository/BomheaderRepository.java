package com.spring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.app.domain.Bomheader;
import com.spring.app.domain.Itemmtr;

/**
 * Spring Data JPA repository for the Bomheader entity.
 */
public interface BomheaderRepository extends JpaRepository<Bomheader,Long> {
	
	Bomheader findByItemmtr(Itemmtr itemmtr);

}
