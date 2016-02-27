package com.hipstercrm.repository;

import com.hipstercrm.domain.Deliveryday;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Deliveryday entity.
 */
public interface DeliverydayRepository extends JpaRepository<Deliveryday,Long> {

}
