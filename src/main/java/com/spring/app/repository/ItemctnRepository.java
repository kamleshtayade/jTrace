package com.spring.app.repository;

import com.spring.app.domain.Itemctn;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemctn entity.
 */
public interface ItemctnRepository extends JpaRepository<Itemctn,Long> {
	
	Long countByLotCode(String lotCode);
}
