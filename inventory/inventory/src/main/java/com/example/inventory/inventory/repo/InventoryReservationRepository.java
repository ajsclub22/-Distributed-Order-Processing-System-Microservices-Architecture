package com.example.inventory.inventory.repo;

import com.example.inventory.inventory.entities.Inventory;
import com.example.inventory.inventory.entities.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, Long> {
    boolean existsByOrderId(Long orderId);

    Optional<InventoryReservation> findByOrderId(Long id);
}
