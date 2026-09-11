package com.example.demo.service;

import com.example.demo.dto.LibroRequest;
import com.example.demo.dto.UsuarioRequest;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Libro;
import com.example.demo.model.Usuario;
import com.example.demo.repository.LibroRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibreriaService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public LibreriaService(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerTodosLibros() {
        return libroRepository.findAll();
    } 
    public List<Libro> obtenerLibrosReservados() {
        return libroRepository.findAll().stream()
                .filter(Libro::getReservado)
                .toList();
    }
    public Libro guardarLibro(LibroRequest request) {
        return libroRepository.save(new Libro(request.nombre(), request.autor(), request.anio()));
    }

    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }

    public Usuario guardarUsuario(UsuarioRequest request) {
        return usuarioRepository.save(new Usuario(request.nombre(), request.email()));
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Libro reservarLibro(Long libroId, Long usuarioId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + libroId));
        if (Boolean.TRUE.equals(libro.getReservado())) {
            throw new ConflictException("El libro ya se encuentra reservado");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        if (libroRepository.countByUsuarioReservadoId(usuarioId) >= 3) {
            throw new ConflictException("El usuario ya tiene el limite maximo de 3 libros reservados");
        }

        libro.setReservado(true);
        libro.setUsuarioReservado(usuario);
        return libroRepository.save(libro);
    }

}
