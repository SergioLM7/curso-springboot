package net.sergio.service;

import java.util.List;

import org.springframework.data.domain.Example;

import net.sergio.model.Vacante;

public interface IVacanteService {

    List<Vacante> buscarTodas();

    Vacante buscarPorId(Integer idVacante);

    void guardar(Vacante vacante);

    List<Vacante> buscarDestacadas();

    void eliminar(Integer id);

    List<Vacante> searchByExample(Example<Vacante> example);

}
