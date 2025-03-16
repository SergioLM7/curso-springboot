package com.slillo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slillo.model.Categoria;

//public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
