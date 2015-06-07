package com.spring.app.repository;

import com.spring.app.domain.Isupplier;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Isupplier entity.
 */
public interface IsupplierRepository extends JpaRepository<Isupplier,Long> {

    @Query("select isupplier from Isupplier isupplier left join fetch isupplier.imanufacturers where isupplier.id =:id")
    Isupplier findOneWithEagerRelationships(@Param("id") Long id);

}
