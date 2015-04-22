package com.spring.app.repository;

import com.spring.app.domain.Itemcat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Itemcat entity.
 */
public interface ItemcatRepository extends JpaRepository<Itemcat,Long>{

}
