package TP.EstacionesMicroServicio.service;

import TP.EstacionesMicroServicio.entity.Estacion;
import TP.EstacionesMicroServicio.repository.EstacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionesService {
  @Autowired
  EstacionesRepository repository;

  public List<Estacion> findAll() {
    return repository.findAll();
  }
  public Optional<Estacion> findOne(int id){
    return repository.findById(id);
  }

  public Estacion create(Estacion estacion){
    LocalDateTime fecha = LocalDateTime.now();
    estacion.setFECHA_HORA_CREACION(fecha);
    return repository.save(estacion);

  }
  public void deleteEstacion(int id){
    repository.deleteById(id);
  }

  public void modificarEstacion(Estacion estacion){
    Estacion estacion_find = repository.findById(estacion.getID()).orElse(null);
    assert estacion_find != null;
    estacion_find.setNOMBRE(estacion.getNOMBRE());
    estacion_find.setLATITUD(estacion.getLATITUD());
    estacion_find.setLONGITUD(estacion.getLONGITUD());
    repository.save(estacion_find);
  }

  public Estacion getByCercania(Float longitud, Float latitud){
    List<Estacion> estaciones = repository.findAll();
    Iterator iteradorEstaciones = estaciones.iterator();
    Estacion estacionCercana = (Estacion)iteradorEstaciones.next();
    while(iteradorEstaciones.hasNext()){
      Estacion siguiente = (Estacion)iteradorEstaciones.next();
      if(siguiente.calcularDistancia(longitud,latitud) < estacionCercana.calcularDistancia(longitud,latitud)){
        estacionCercana = siguiente;
      }
    }
    return estacionCercana;
  }





}
