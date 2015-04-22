package com.spring.app.repository;

import com.spring.app.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Plant entity.
 */
public interface PlantRepository extends JpaRepository<Plant,Long>{

}
