package com.spring.app.repository;

import com.spring.app.domain.Workorderheader;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Workorderheader entity.
 */
public interface WorkorderheaderRepository extends JpaRepository<Workorderheader,Long>{

}
