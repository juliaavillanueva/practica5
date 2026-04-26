package com.icai.dpi_carrito.service;

import com.icai.dpi_carrito.dto.CrearCarritoRequest;
import com.icai.dpi_carrito.dto.LineaCarritoRequest;
import com.icai.dpi_carrito.entity.CarritoEntity;
import com.icai.dpi_carrito.entity.LineaCarritoEntity;
import com.icai.dpi_carrito.exception.NotFoundException;
import com.icai.dpi_carrito.repository.CarritoRepository;
import com.icai.dpi_carrito.repository.LineaCarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final LineaCarritoRepository lineaRepository;

    public CarritoService(CarritoRepository carritoRepository, LineaCarritoRepository lineaRepository) {
        this.carritoRepository = carritoRepository;
        this.lineaRepository = lineaRepository;
    }

    // -------- Carrito CRUD --------

    public CarritoEntity crearCarrito(CrearCarritoRequest req) {
        CarritoEntity c = new CarritoEntity();
        c.setIdUsuario(req.getIdUsuario());
        c.setCorreoUsuario(req.getCorreoUsuario());
        c.setTotalPrecio(0.0);
        return carritoRepository.save(c);
    }

    public List<CarritoEntity> listarCarritos() {
        return carritoRepository.findAll();
    }

    public CarritoEntity obtenerCarrito(Long idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new NotFoundException("No existe carrito con idCarrito=" + idCarrito));
    }

    public void borrarCarrito(Long idCarrito) {
        if (!carritoRepository.existsById(idCarrito)) {
            throw new NotFoundException("No existe carrito con idCarrito=" + idCarrito);
        }
        carritoRepository.deleteById(idCarrito);
    }

    // -------- Líneas (lo nuevo que pide el enunciado) --------

    @Transactional
    public CarritoEntity anadirLinea(Long idCarrito, LineaCarritoRequest req) {
        CarritoEntity carrito = obtenerCarrito(idCarrito);

        // Si ya existe la línea para ese artículo, sumamos unidades (típico)
        LineaCarritoEntity linea = lineaRepository
                .findByCarrito_IdCarritoAndIdArticulo(idCarrito, req.getIdArticulo())
                .orElseGet(LineaCarritoEntity::new);

        linea.setCarrito(carrito);
        linea.setIdArticulo(req.getIdArticulo());
        linea.setPrecioUnitario(req.getPrecioUnitario());

        int unidadesFinales = req.getNumeroUnidades();
        if (linea.getNumeroUnidades() != null) {
            unidadesFinales = linea.getNumeroUnidades() + req.getNumeroUnidades();
        }
        linea.setNumeroUnidades(unidadesFinales);

        double costeLinea = linea.getPrecioUnitario() * linea.getNumeroUnidades();
        linea.setCosteLineaArticulo(costeLinea);

        lineaRepository.save(linea);

        // Recalcular totalPrecio del carrito
        recalcularTotal(carrito);

        return carritoRepository.save(carrito);
    }

    @Transactional
    public CarritoEntity borrarLinea(Long idCarrito, Long idArticulo) {
        CarritoEntity carrito = obtenerCarrito(idCarrito);

        var existente = lineaRepository.findByCarrito_IdCarritoAndIdArticulo(idCarrito, idArticulo)
                .orElseThrow(() -> new NotFoundException(
                        "No existe línea con idArticulo=" + idArticulo + " en carrito=" + idCarrito
                ));

        lineaRepository.delete(existente);

        recalcularTotal(carrito);
        return carritoRepository.save(carrito);
    }

    private void recalcularTotal(CarritoEntity carrito) {
        // fuerza a usar las líneas del carrito (ya asociadas) -> sumatorio
        double total = carrito.getLineas().stream()
                .mapToDouble(LineaCarritoEntity::getCosteLineaArticulo)
                .sum();

        carrito.setTotalPrecio(total);
    }
}