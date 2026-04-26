package com.icai.dpi_carrito.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class CrearCarritoRequest {

    @NotNull
    private Long idUsuario;

    @NotNull
    @Email
    private String correoUsuario;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
}