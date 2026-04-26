package com.icai.dpi_carrito.entity;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private String correoUsuario;

    @Column(nullable = false)
    private Double totalPrecio = 0.0;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaCarritoEntity> lineas = new ArrayList<>();

    public CarritoEntity() {}

    // getters/setters
    public Long getIdCarrito() { return idCarrito; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public Double getTotalPrecio() { return totalPrecio; }
    public void setTotalPrecio(Double totalPrecio) { this.totalPrecio = totalPrecio; }

    public List<LineaCarritoEntity> getLineas() {
        return lineas; }
}