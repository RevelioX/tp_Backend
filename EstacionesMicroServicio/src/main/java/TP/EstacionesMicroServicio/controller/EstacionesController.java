package TP.EstacionesMicroServicio.controller;


import TP.EstacionesMicroServicio.entity.Estacion;
import TP.EstacionesMicroServicio.service.EstacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EstacionesController {

  @Autowired
  private EstacionesService service;

  @GetMapping("/estaciones")
  public List<Estacion> findAllArtist() {
    return service.findAll();
  }

  @GetMapping("/estaciones/{id}")
  public Optional<Estacion> findOneEstacion(@PathVariable int id) {
    return service.findOne(id);
  }

  @PostMapping("/api/estaciones")
  public ResponseEntity<?> addEstacion(@RequestBody Estacion estacion) {
    try {
      service.create(estacion);
      return ResponseEntity.status(201).body(estacion);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  @DeleteMapping("/estaciones/{id}")
  public ResponseEntity<?> deleteEstacion(@PathVariable int id) {
    try {
      service.deleteEstacion(id);
      return ResponseEntity.status(204).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).body("No se encontro el id");
    }
  }

  @PutMapping("/api/estaciones")
  public ResponseEntity<?> putEstacion(@RequestParam Estacion estacion) {
    try {
      service.modificarEstacion(estacion);
      return ResponseEntity.status(201).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).body("No se logro hacer el put");
    }
  }

  @GetMapping("/api/estaciones")
  public ResponseEntity<Estacion> getEstacionCercana(@RequestParam(value = "longitud", required = false) Float longitud,
                                                     @RequestParam(value = "latitud", required = false) Float latitud) {
    System.out.println("Arveja");
    try{

    Estacion estacion = service.getByCercania(longitud, latitud);
    return ResponseEntity.ok(estacion);
  }catch (Exception e){
    return ResponseEntity.status(400).build();
  }
  }

}
