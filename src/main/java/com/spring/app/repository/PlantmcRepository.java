package com.spring.app.repository;

import com.spring.app.domain.Plantmc;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plantmc entity.
 */
public interface PlantmcRepository extends JpaRepository<Plantmc,Long> {

}
