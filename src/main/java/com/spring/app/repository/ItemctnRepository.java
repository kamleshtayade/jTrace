package com.spring.app.repository;

import com.spring.app.domain.Itemctn;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Itemctn entity.
 */
public interface ItemctnRepository extends JpaRepository<Itemctn,Long>{

}