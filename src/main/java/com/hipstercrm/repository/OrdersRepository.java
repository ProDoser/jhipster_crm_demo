package com.hipstercrm.repository;

import com.hipstercrm.domain.Orders;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Orders entity.
 */
public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
