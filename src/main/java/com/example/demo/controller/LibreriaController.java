package com.example.demo.controller;

import com.example.demo.dto.LibroRequest;
import com.example.demo.dto.UsuarioRequest;
import com.example.demo.model.Libro;
import com.example.demo.model.Usuario;
import com.example.demo.service.LibreriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Libreria", description = "Libros, usuarios y reservas")
public class LibreriaController {

    private final LibreriaService libreriaService;

    public LibreriaController(LibreriaService libreriaService) {
        this.libreriaService = libreriaService;
    }

    @GetMapping("/libros")
    @Operation(summary = "Obtener todos los libros")
    public List<Libro> listarLibros() {
        return libreriaService.obtenerTodosLibros();
    }

    @PostMapping("/libros")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un libro")
    public Libro crearLibro(@Valid @RequestBody LibroRequest request) {
        return libreriaService.guardarLibro(request);
    }

    @DeleteMapping("/libros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un libro")
    public void eliminarLibro(@PathVariable Long id) {
        libreriaService.eliminarLibro(id);
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un usuario")
    public Usuario crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return libreriaService.guardarUsuario(request);
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios")
    public List<Usuario> listarUsuarios() {
        return libreriaService.obtenerUsuarios();
    }

    @PostMapping("/libros/{libroId}/reservar/{usuarioId}")
    @Operation(summary = "Reservar un libro, maximo 3 por usuario")
    public Libro reservarLibro(@PathVariable Long libroId, @PathVariable Long usuarioId) {
        return libreriaService.reservarLibro(libroId, usuarioId);
    }
    @GetMapping("/reservas")
    @Operation(summary = "Lista todos los libros reservados")
    public List<Libro> listarLibrosReservados() {
        return libreriaService.obtenerLibrosReservados();
    }
}
