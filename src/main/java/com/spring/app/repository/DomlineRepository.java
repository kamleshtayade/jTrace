package com.spring.app.repository;

import com.spring.app.domain.Domline;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domline entity.
 */
public interface DomlineRepository extends JpaRepository<Domline,Long> {

}
