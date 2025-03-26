package net.sergio.service;

import java.util.List;

import net.sergio.model.Categoria;

public interface ICategoriaService {
    void guardar(Categoria categoria);

    List<Categoria> buscarTodas();

    Categoria buscarPorId(Integer idCategoria);

    void eliminar(Integer idCategoria);
}