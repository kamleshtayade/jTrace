package com.spring.app.repository;

import com.spring.app.domain.Workorderline;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Workorderline entity.
 */
public interface WorkorderlineRepository extends JpaRepository<Workorderline,Long> {

}
