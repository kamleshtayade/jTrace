package com.spring.app.repository;

import com.spring.app.domain.Imanufacturer;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Imanufacturer entity.
 */
public interface ImanufacturerRepository extends JpaRepository<Imanufacturer,Long> {

}
