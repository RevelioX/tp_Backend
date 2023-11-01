package TP.AlquileresMicroServicio.controllers;

import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.services.interfaces.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    @Autowired
    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Tarifa>> getAllTarifas() {
        List<Tarifa> tarifas = tarifaService.getAll();
        if (tarifas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(tarifas, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getTarifaById(@PathVariable Long id) {
        return tarifaService.getById(id)
                .map(tarifa -> new ResponseEntity<>(tarifa, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<String> createTarifa(@RequestBody Tarifa tarifa) {
        if (tarifa.getID() == null) {
            return new ResponseEntity<>("Id es Obligatorio", HttpStatus.BAD_REQUEST);
        }

        Tarifa nueva_tarifa = tarifaService.add(tarifa);

        if (nueva_tarifa != null) {
            return new ResponseEntity<>("Tarifa creada con éxito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al crear la tarifa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTarifa(@PathVariable Long id) {
        try {
            tarifaService.delete(id);
            return new ResponseEntity<>("Tarifa eliminada con éxito", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("Tarifa no encontrada", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error en la eliminación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateTarifa(@RequestBody Tarifa tarifa){
        Optional<Tarifa> tarifa_find = tarifaService.getById(tarifa.getID());

        if (tarifa_find.isEmpty()) {
            return new ResponseEntity<>("Tarifa no encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            tarifaService.update(tarifa);
            return new ResponseEntity<>("Tarifa actualizada con éxito", HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Error en la actualización de Tarifa: Violación de integridad de datos", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Error en la actualización de Tarifa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
