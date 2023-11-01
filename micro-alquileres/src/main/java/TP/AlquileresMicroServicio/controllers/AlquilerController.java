package TP.AlquileresMicroServicio.controllers;

import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.services.interfaces.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {


    private final AlquilerService alquilerService;

    @Autowired
    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
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

}
