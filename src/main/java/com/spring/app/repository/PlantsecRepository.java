package com.spring.app.repository;

import com.spring.app.domain.Plantsec;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plantsec entity.
 */
public interface PlantsecRepository extends JpaRepository<Plantsec,Long> {

}
