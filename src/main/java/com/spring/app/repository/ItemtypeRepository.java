package com.spring.app.repository;

import com.spring.app.domain.Itemtype;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Itemtype entity.
 */
public interface ItemtypeRepository extends JpaRepository<Itemtype,Long>{

}
