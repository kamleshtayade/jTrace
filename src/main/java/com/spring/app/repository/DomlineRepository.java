package com.spring.app.repository;

import com.spring.app.domain.Domline;
import com.spring.app.domain.Workorderheader;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domline entity.
 */
public interface DomlineRepository extends JpaRepository<Domline,Long> {
	
	// Domline by serial no
	Domline findBySerialno(String serialno);

}
