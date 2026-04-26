package com.icai.dpi_carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LineaCarritoRequest {

    @NotNull
    private Long idArticulo;

    @NotNull
    @Min(0)
    private Double precioUnitario;

    @NotNull
    @Min(1)
    private Integer numeroUnidades;

    public Long getIdArticulo() { return idArticulo; }
    public void setIdArticulo(Long idArticulo) { this.idArticulo = idArticulo; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getNumeroUnidades() { return numeroUnidades; }
    public void setNumeroUnidades(Integer numeroUnidades) { this.numeroUnidades = numeroUnidades; }
}