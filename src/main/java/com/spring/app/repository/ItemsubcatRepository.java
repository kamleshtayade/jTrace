package com.spring.app.repository;

import com.spring.app.domain.Itemsubcat;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemsubcat entity.
 */
public interface ItemsubcatRepository extends JpaRepository<Itemsubcat,Long> {

}
