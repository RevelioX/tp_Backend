package TP.EstacionesMicroServicio.controller;


import TP.EstacionesMicroServicio.entity.Estacion;
import TP.EstacionesMicroServicio.service.EstacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionesController {

  @Autowired
  private EstacionesService service;

  @GetMapping
  public List<Estacion> findAllEstaciones() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Estacion> findOneEstacion(@PathVariable int id) {
    return service.findOne(id);
  }

  @PostMapping
  public ResponseEntity<?> addEstacion(@RequestBody Estacion estacion) {
    try {
      service.create(estacion);
      return ResponseEntity.status(201).body(estacion);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEstacion(@PathVariable int id) {
    try {
      service.deleteEstacion(id);
      return ResponseEntity.status(204).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).body("No se encontro el id");
    }
  }

  @PutMapping
  public ResponseEntity<?> putEstacion(@RequestParam Estacion estacion) {
    try {
      service.modificarEstacion(estacion);
      return ResponseEntity.status(201).build();
    } catch (Exception e) {
      return ResponseEntity.status(400).body("No se logro hacer el put");
    }
  }

  @GetMapping("/cercana")
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

  //todo Agregar Endpoint que Ingresen 2 id de estaciones y retorne la distancia entre las dos en mts.
  @GetMapping("/distancia/{id1}/{id2}")
  public ResponseEntity<?> getDistancia(@PathVariable int id1, @PathVariable int id2){
    try{
      Float distancia = service.getDistacia(id1,id2);
      return ResponseEntity.ok(distancia);
    }catch (Exception e){
      return ResponseEntity.status(400).build();
    }
  }
}
