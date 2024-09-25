package edu.pe.cibertec.viewmodel;

public record VehiculoViewModel(
        String codigo,
        String mensaje,
        String autoMarca,
        String vehiModelo,
        String vehiNroAsientos,
        String vehiPrecio,
        String vehiColor
) {
}
