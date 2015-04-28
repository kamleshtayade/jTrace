package com.spring.app.repository;

import com.spring.app.domain.Itemmaster;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemmaster entity.
 */
public interface ItemmasterRepository extends JpaRepository<Itemmaster,Long> {

}
