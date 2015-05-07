package com.spring.app.repository;

import com.spring.app.domain.Itemmfrpart;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Itemmfrpart entity.
 */
public interface ItemmfrpartRepository extends JpaRepository<Itemmfrpart,Long> {

}
