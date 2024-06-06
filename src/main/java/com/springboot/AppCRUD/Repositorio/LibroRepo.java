package com.springboot.AppCRUD.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.AppCRUD.Modelo.Libro;


@Repository
public interface LibroRepo extends JpaRepository<Libro, Long> {

}