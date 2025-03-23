package net.sergio.service;

import java.util.List;

import net.sergio.model.Vacante;

public interface IVacantesService {

    List<Vacante> buscarTodas();

    Vacante buscarPorId(Integer idVacante);

    void guardar(Vacante vacante);

    List<Vacante> buscarDestacadas();

}
