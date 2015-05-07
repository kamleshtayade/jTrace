package com.spring.app.repository;

import com.spring.app.domain.Plantmfgline;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plantmfgline entity.
 */
public interface PlantmfglineRepository extends JpaRepository<Plantmfgline,Long> {

}
