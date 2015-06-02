package com.spring.app.repository;

import com.spring.app.domain.Supplier;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Supplier entity.
 */
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("select supplier from Supplier supplier left join fetch supplier.manufacturers where supplier.id =:id")
    Supplier findOneWithEagerRelationships(@Param("id") Long id);
}
