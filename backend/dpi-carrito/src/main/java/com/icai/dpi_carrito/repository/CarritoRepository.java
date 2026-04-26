package com.icai.dpi_carrito.repository;

import com.icai.dpi_carrito.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {
}