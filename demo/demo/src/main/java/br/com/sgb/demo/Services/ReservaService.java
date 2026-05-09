package br.com.sgb.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.dtos.ReservaDto;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.entities.Reserva;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.LivroRepository;
import br.com.sgb.demo.repositories.ReservaRepository;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class ReservaService {

    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public ReservaService(ReservaRepository repository, UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public List<ReservaDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ReservaDto> findById(int id) {
        return repository.findById(id).map(this::toDto);
    }

    public List<ReservaDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByUsuarioMatricula(matriculaUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDto> findByIsbnLivro(String isbn) {
        return repository.findByLivroIsbn(isbn).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDto> findByEstadoReserva(int estado) {
        return repository.findByEstadoReserva(estado).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDto> findByMatriculaAndIsbn(int matriculaUsuario, String isbnLivro) {
        return repository.findByUsuarioMatriculaAndLivroIsbn(matriculaUsuario, isbnLivro).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDto> findByDataLimiteBefore(LocalDateTime data) {
        return repository.findByDataLimiteBefore(data).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDto> findByDataLimiteAfter(LocalDateTime data) {
        return repository.findByDataLimiteAfter(data).stream().map(this::toDto).collect(Collectors.toList());
    }

    public ReservaDto save(ReservaDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    public ReservaDto update(int id, ReservaDto dto) {
        repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva nao encontrada"));
        dto.setIdReserva(id);
        return toDto(repository.save(toEntity(dto)));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    private ReservaDto toDto(Reserva entity) {
        return new ReservaDto(
                entity.getIdReserva(),
                entity.getUsuario().getMatricula(),
                entity.getLivro().getIsbn(),
                entity.getDataLimite(),
                entity.getEstadoReserva());
    }

    private Reserva toEntity(ReservaDto dto) {
        Usuario usuario = usuarioRepository.findByMatricula(dto.getMatriculaUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuario nao encontrado"));
        Livro livro = livroRepository.findByIsbn(dto.getIsbnLivro())
                .orElseThrow(() -> new NoSuchElementException("Livro nao encontrado"));

        Reserva entity = new Reserva();
        if (dto.getIdReserva() != null && dto.getIdReserva() > 0) {
            entity.setIdReserva(dto.getIdReserva());
        }
        entity.setUsuario(usuario);
        entity.setLivro(livro);
        entity.setDataLimite(dto.getDataLimite());
        entity.setEstadoReserva(dto.getEstadoReserva());
        return entity;
    }
}
