package com.fcc.simulation.repository;

import com.fcc.simulation.entity.WhiskyInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhiskyInventoryRepository extends JpaRepository<WhiskyInventory, Long> {
    List<WhiskyInventory> findAllByOrderBySortOrderAsc();
}
