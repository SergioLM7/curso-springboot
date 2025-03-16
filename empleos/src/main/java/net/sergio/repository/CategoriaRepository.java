package net.sergio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sergio.model.Categoria;

//public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
