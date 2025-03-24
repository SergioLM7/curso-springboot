package net.sergio.service;

import java.util.List;

import net.sergio.model.Usuario;

public interface IUsuarioService {

    void guardar(Usuario usuario);

    void eliminar(Integer idUsuario);

    List<Usuario> buscarTodos();

}
