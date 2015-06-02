package com.spring.app.repository;

import com.spring.app.domain.Bomheader;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bomheader entity.
 */
public interface BomheaderRepository extends JpaRepository<Bomheader,Long> {

}
