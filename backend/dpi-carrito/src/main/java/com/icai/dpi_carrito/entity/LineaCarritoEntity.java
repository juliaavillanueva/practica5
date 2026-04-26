package com.icai.dpi_carrito.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(
        name = "lineas_carrito",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_carrito", "idArticulo"})
)
public class LineaCarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrito")
    private CarritoEntity carrito;

    @Column(nullable = false)
    private Long idArticulo;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Integer numeroUnidades;

    @Column(nullable = false)
    private Double costeLineaArticulo;

    public LineaCarritoEntity() {}

    // getters/setters
    public Long getIdLinea() { return idLinea; }

    public CarritoEntity getCarrito() { return carrito; }
    public void setCarrito(CarritoEntity carrito) { this.carrito = carrito; }

    public Long getIdArticulo() { return idArticulo; }
    public void setIdArticulo(Long idArticulo) { this.idArticulo = idArticulo; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getNumeroUnidades() { return numeroUnidades; }
    public void setNumeroUnidades(Integer numeroUnidades) { this.numeroUnidades = numeroUnidades; }

    public Double getCosteLineaArticulo() { return costeLineaArticulo; }
    public void setCosteLineaArticulo(Double costeLineaArticulo) { this.costeLineaArticulo = costeLineaArticulo; }
}