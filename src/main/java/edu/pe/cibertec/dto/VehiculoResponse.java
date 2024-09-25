package edu.pe.cibertec.dto;

public record VehiculoResponse(
        String codigo,
        String mensaje,
        String vehiculoMarca,
        String vehiculoModelo,
        String vehiculoNroAsientos,
        String vehiculoPrecio,
        String vehiculoColor
) {
}
