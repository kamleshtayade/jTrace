package com.spring.app.repository;

import com.spring.app.domain.Itemtype;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemtype entity.
 */
public interface ItemtypeRepository extends JpaRepository<Itemtype,Long> {

}
