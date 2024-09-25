package edu.pe.cibertec.controller;

import edu.pe.cibertec.dto.VehiculoRequest;
import edu.pe.cibertec.dto.VehiculoResponse;
import edu.pe.cibertec.viewmodel.VehiculoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/buscarVehiculo")
    public String buscarVehiculo(Model model) {
        VehiculoViewModel vehiculoModel = new VehiculoViewModel(
                "00", "", "", "", "", "", "");
        model.addAttribute("vehiculoModel", vehiculoModel);
        return "buscar";
    }

    @PostMapping("/buscandoVehiculo")
    public String buscandoVehiculo(
            @RequestParam("placa") String placa,
            Model model) {
        if (placa == null || placa.trim().isEmpty()) {
            VehiculoViewModel vehiculoModel = new VehiculoViewModel(
                    "01", "Placa incorrecta.",
                    "", "", "", "", "");
            model.addAttribute("vehiculoModel", vehiculoModel);
            return "buscar";
        }

        try {
            String endpoint = "http://localhost:8080/vehiculos";
            VehiculoRequest vehiculoRequestDto = new VehiculoRequest(placa);
            VehiculoResponse vehiculoResponseDto = restTemplate.postForObject(endpoint, vehiculoRequestDto, VehiculoResponse.class);
            if (vehiculoResponseDto.codigo().equals("00")) {
                VehiculoViewModel vehiculoModel = new VehiculoViewModel(
                        "00", "", vehiculoResponseDto.vehiculoMarca(), vehiculoResponseDto.vehiculoModelo(), vehiculoResponseDto.vehiculoNroAsientos(),
                        vehiculoResponseDto.vehiculoPrecio(), vehiculoResponseDto.vehiculoColor());
                model.addAttribute("vehiculoModel", vehiculoModel);
                return "detalles";
            } else {
                VehiculoViewModel vehiculoModel = new VehiculoViewModel(
                        "01", "Vehículo no encontrado.",
                        "", "", "", "", "");
                model.addAttribute("vehiculoModel", vehiculoModel);
                return "buscar";
            }
        } catch(Exception e) {
            VehiculoViewModel vehiculoModel = new VehiculoViewModel(
                    "99", "Error al buscar vehículo.",
                    "", "", "", "", "");
            model.addAttribute("vehiculoModel", vehiculoModel);
            System.out.println(e.getMessage());
            return "buscar";
        }
    }
}
