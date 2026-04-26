package com.icai.dpi_carrito.controller;

import com.icai.dpi_carrito.dto.CrearCarritoRequest;
import com.icai.dpi_carrito.dto.LineaCarritoRequest;
import com.icai.dpi_carrito.entity.CarritoEntity;
import com.icai.dpi_carrito.service.CarritoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }

    @PostMapping
    public CarritoEntity crearCarrito(@RequestBody CrearCarritoRequest req) {
        return carritoService.crearCarrito(req);
    }

    @GetMapping
    public List<CarritoEntity> listarCarritos() {
        return carritoService.listarCarritos();
    }

    @GetMapping("/{idCarrito}")
    public CarritoEntity obtenerCarrito(@PathVariable Long idCarrito) {
        return carritoService.obtenerCarrito(idCarrito);
    }

    @DeleteMapping("/{idCarrito}")
    public void borrarCarrito(@PathVariable Long idCarrito) {
        carritoService.borrarCarrito(idCarrito);
    }

    @PostMapping("/{idCarrito}/lineas")
    public CarritoEntity anadirLinea(@PathVariable Long idCarrito,
                                     @RequestBody LineaCarritoRequest req) {
        return carritoService.anadirLinea(idCarrito, req);
    }

    @DeleteMapping("/{idCarrito}/lineas/{idArticulo}")
    public CarritoEntity borrarLinea(@PathVariable Long idCarrito,
                                     @PathVariable Long idArticulo) {
        return carritoService.borrarLinea(idCarrito, idArticulo);
    }
}
