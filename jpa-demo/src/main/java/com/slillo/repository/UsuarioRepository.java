package com.slillo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slillo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
