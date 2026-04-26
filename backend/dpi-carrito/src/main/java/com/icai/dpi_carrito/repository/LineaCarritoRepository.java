package com.icai.dpi_carrito.repository;

import com.icai.dpi_carrito.entity.LineaCarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LineaCarritoRepository extends JpaRepository<LineaCarritoEntity, Long> {
    Optional<LineaCarritoEntity> findByCarrito_IdCarritoAndIdArticulo(Long idCarrito, Long idArticulo);
    void deleteByCarrito_IdCarritoAndIdArticulo(Long idCarrito, Long idArticulo);
    List<LineaCarritoEntity> findByCarrito_IdCarrito(Long idCarrito);
}