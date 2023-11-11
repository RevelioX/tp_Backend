package TP.AlquileresMicroServicio.controllers;

import TP.AlquileresMicroServicio.controllers.responses.AlquilerResponse;
import TP.AlquileresMicroServicio.controllers.responses.EstacionResponse;
import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.services.ConvertidorService;
import TP.AlquileresMicroServicio.services.interfaces.AlquilerService;
import TP.AlquileresMicroServicio.services.interfaces.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {


    private final AlquilerService alquilerService;

    private final TarifaService tarifaService;

    private final RestTemplate restTemplate;

    private final ConvertidorService convertidorService;

    @Autowired
    public AlquilerController(AlquilerService alquilerService, TarifaService tarifaService, RestTemplate restTemplate, ConvertidorService convertidorService) {
        this.alquilerService = alquilerService;
        this.tarifaService = tarifaService;
        this.restTemplate = restTemplate;
        this.convertidorService = convertidorService;
    }


    @GetMapping("/")
    public ResponseEntity<List<Alquiler>> getAllAlquiler() {
        List<Alquiler> alquileres = alquilerService.getAll();
        if (alquileres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(alquileres, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alquiler> getAlquilerById(@PathVariable Long id) {
        return alquilerService.getById(id)
                .map(alquiler -> new ResponseEntity<>(alquiler, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<String> createAlquiler(@RequestBody Alquiler alquiler) {

        if (alquiler.getID() == null) {
            return new ResponseEntity<>("Id es Obligatorio", HttpStatus.BAD_REQUEST);
        }

        Alquiler nuevo_alquiler = alquilerService.add(alquiler);

        if (nuevo_alquiler != null) {
            return new ResponseEntity<>("Alquiler creado con éxito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al crear el Alquiler", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlquiler(@PathVariable Long id) {
        try {
            alquilerService.delete(id);
            return new ResponseEntity<>("Alquiler eliminado con éxito", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("Alquiler no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error en la eliminación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateAlquiler(@RequestBody Alquiler alquiler){
        Optional<Alquiler> tarifa_find = alquilerService.getById(alquiler.getID());

        if (tarifa_find.isEmpty()) {
            return new ResponseEntity<>("Alquiler no encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            alquilerService.update(alquiler);
            return new ResponseEntity<>("Alquiler actualizado con éxito", HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Error en la actualización de Alquiler: Violación de integridad de datos", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Error en la actualización de Alquiler", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/iniciarAlquiler")
    public ResponseEntity<String> inicarAlquiler(@RequestParam Long clienteId,@RequestParam Long estacionId){

        Tarifa tarifaHoy = tarifaService.getTarifaHoy();

        //Corroboro la existencia de la estacion
        String url = "http://localhost:7070/api/estaciones/" + estacionId;
        EstacionResponse estacionResponse = restTemplate.getForObject(url, EstacionResponse.class);
        if (estacionResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estación no encontrada.");
        }
        //TODO llevar el manejo de respuesta http al service para mayor control, porque aca estoy asumiendo que el service lograra hacer el post sin problemas
        alquilerService.iniciarAlquiler(clienteId,estacionId,tarifaHoy);
        return ResponseEntity.status(HttpStatus.OK).body("Alquiler iniciado con éxito.");
    }

    @PutMapping("/finalizarAlquiler")
    public ResponseEntity<AlquilerResponse> finalizarAlquiler(@RequestParam Long alquilerId, @RequestParam Long estacionId, @RequestParam String moneda){

        //Corroboro la existencia de la estacion
        String url = "http://localhost:7070/api/estaciones/" + estacionId;
        EstacionResponse estacionResponse = restTemplate.getForObject(url, EstacionResponse.class);
        Optional<Alquiler> alquilerOptional = alquilerService.getById(alquilerId);
        //Corroboro que el alquiler y la estacion existan
        if (estacionResponse == null || alquilerOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        //TODO llevar el manejo de respuesta http al service para mayor control
        Alquiler alquilerFin = alquilerService.finalizarAlquiler(alquilerId,estacionId);
        double monedaConvertida =convertidorService.convertirMoneda(alquilerFin.getMONTO(),moneda);
        //Redonde a 2 decimales
        monedaConvertida = Math.round(monedaConvertida * 100.0) / 100.0;
        AlquilerResponse response = new AlquilerResponse(
                alquilerFin.getID_CLIENTE(),
                alquilerFin.getESTADO(),
                alquilerFin.getESTACION_RETIRO(),
                alquilerFin.getESTACION_DEVOLUCION(),
                alquilerFin.getFECHA_HORA_DEVOLUCION(),
                alquilerFin.getFECHA_HORA_RETIRO(),
                monedaConvertida
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
