package com.springboot.AppCRUD.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.AppCRUD.Modelo.Libro;
import com.springboot.AppCRUD.Repositorio.LibroRepo;


@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroRepo libroRepo;

    @GetMapping("/getAllLibros")
    public ResponseEntity<List<Libro>> getAllLibros() {
        try {
            List<Libro> libroLista = new ArrayList<>();
            libroRepo.findAll().forEach(libroLista::add);

            if (libroLista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(libroLista, HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/getLibroById/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libroData = libroRepo.findById(id);

        if(libroData.isPresent()) {
            return new ResponseEntity<>(libroData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addLibro")
    public ResponseEntity<Libro> addLibro(@RequestBody Libro libro) {
        try {
            Libro libroObj = libroRepo.save(libro);
            return new ResponseEntity<>(libroObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateLibroById/{id}")
    public ResponseEntity<Libro> updateLibroById(@PathVariable Long id, @RequestBody Libro newLibroData) {
        Optional<Libro> oldLibroData = libroRepo.findById(id);

        if (oldLibroData.isPresent()) {
            Libro updateLibroData = oldLibroData.get();
            if (newLibroData.getTitulo() != null) {
                updateLibroData.setTitulo(newLibroData.getTitulo());
            }
            if (newLibroData.getAutor() != null) {
                updateLibroData.setAutor(newLibroData.getAutor());
            }

            Libro libroObj = libroRepo.save(updateLibroData);
            return new ResponseEntity<>(libroObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteLibroById/{id}")
    public ResponseEntity<HttpStatus> deleteLibroById(@PathVariable Long id) {
        try {
            libroRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

