package com.spring.app.repository;

import com.spring.app.domain.Itemmtr;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemmtr entity.
 */
public interface ItemmtrRepository extends JpaRepository<Itemmtr,Long> {

}
