package com.spring.app.repository;

import com.spring.app.domain.Domheader;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domheader entity.
 */
public interface DomheaderRepository extends JpaRepository<Domheader,Long> {

}
