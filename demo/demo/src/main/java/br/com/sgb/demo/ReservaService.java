package br.com.sgb.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.repositories.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<ReservaDto> findAll() {
        return repository.findAll();
    }

    public Optional<ReservaDto> findById(int id) {
        return repository.findById(id);
    }

    public List<ReservaDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByMatriculaUsuario(matriculaUsuario);
    }

    public List<ReservaDto> findByIsbnLivro(String isbn) {
        return repository.findByIsbnLivro(isbn);
    }

    public List<ReservaDto> findByEstadoReserva(int estado) {
        return repository.findByEstadoReserva(estado);
    }

    public Optional<ReservaDto> findByMatriculaAndIsbn(int matriculaUsuario, String isbnLivro) {
        return repository.findByMatriculaUsuarioAndIsbnLivro(matriculaUsuario, isbnLivro);
    }

    public List<ReservaDto> findByDataLimiteBefore(LocalDate data) {
        return repository.findByDataLimiteBefore(data);
    }

    public List<ReservaDto> findByDataLimiteAfter(LocalDate data) {
        return repository.findByDataLimiteAfter(data);
    }

    public ReservaDto save(ReservaDto dto) {
        return repository.save(dto);
    }

    public ReservaDto update(int id, ReservaDto dto) {
        dto.setIdReserva(id);
        return repository.save(dto);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}