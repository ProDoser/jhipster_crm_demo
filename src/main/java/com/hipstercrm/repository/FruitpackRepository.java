package com.hipstercrm.repository;

import com.hipstercrm.domain.Fruitpack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fruitpack entity.
 */
public interface FruitpackRepository extends JpaRepository<Fruitpack,Long> {

}
