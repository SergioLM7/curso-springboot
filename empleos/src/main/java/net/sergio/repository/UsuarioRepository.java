package net.sergio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sergio.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
