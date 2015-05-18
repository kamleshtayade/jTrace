package com.spring.app.repository;

import com.spring.app.domain.Bomline;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bomline entity.
 */
public interface BomlineRepository extends JpaRepository<Bomline,Long> {

}
