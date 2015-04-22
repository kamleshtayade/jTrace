package com.spring.app.repository;

import com.spring.app.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Supplier entity.
 */
public interface SupplierRepository extends JpaRepository<Supplier,Long>{

}
