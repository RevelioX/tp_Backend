package TP.AlquileresMicroServicio.services.interfaces;

import TP.AlquileresMicroServicio.entities.Tarifa;

import java.util.List;
import java.util.Optional;

public interface Service <T,ID>{

    public T add(T entity);

    public Optional<T> getById(ID id);

    public List<T> getAll();

    public void update(T entity);

    public void delete (ID id);

}