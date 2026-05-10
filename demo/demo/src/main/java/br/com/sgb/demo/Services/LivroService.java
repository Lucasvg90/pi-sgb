package br.com.sgb.demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgb.demo.dtos.LivroDto;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.repositories.EmprestimoRepository;
import br.com.sgb.demo.repositories.LivroRepository;
import br.com.sgb.demo.repositories.ReservaRepository;

@Service
public class LivroService {

    private static final List<String> STATUS_RESERVA_ATIVOS = List.of("ativa", "disponivel");

    private final LivroRepository repository;
    private final EmprestimoRepository emprestimoRepository;
    private final ReservaRepository reservaRepository;

    public LivroService(
            LivroRepository repository,
            EmprestimoRepository emprestimoRepository,
            ReservaRepository reservaRepository) {
        this.repository = repository;
        this.emprestimoRepository = emprestimoRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<LivroDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LivroDto> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn).map(this::toDto);
    }

    @Transactional
    public LivroDto save(LivroDto dto) {
        if (repository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new IllegalStateException("Já existe um livro com este ISBN");
        }

        Livro entity = new Livro();
        preencherCampos(entity, dto);
        if (dto.getUnidadesDisponiveis() <= 0 || dto.getUnidadesDisponiveis() > dto.getUnidades()) {
            entity.setUnidadesDisponiveis(dto.getUnidades());
        }
        return toDto(repository.save(entity));
    }

    @Transactional
    public LivroDto update(String isbn, LivroDto dto) {
        Livro entity = repository.findByIsbn(isbn)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado"));
        preencherCampos(entity, dto);
        entity.setIsbn(isbn);
        entity.setUnidadesDisponiveis(Math.min(entity.getUnidadesDisponiveis(), entity.getUnidades()));
        return toDto(repository.save(entity));
    }

    @Transactional
    public void delete(String isbn) {
        Livro entity = repository.findByIsbn(isbn)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado"));
        repository.delete(entity);
    }

    private void preencherCampos(Livro entity, LivroDto dto) {
        entity.setIsbn(dto.getIsbn());
        entity.setTitulo(dto.getTitulo());
        entity.setAutor(dto.getAutor());
        entity.setGenero(dto.getGenero());
        entity.setAnoPublicacao(dto.getAnoPublicacao());
        entity.setUnidades(dto.getUnidades());
        entity.setUnidadesDisponiveis(dto.getUnidadesDisponiveis());
    }

    private LivroDto toDto(Livro entity) {
        return new LivroDto(
                entity.getIsbn(),
                entity.getTitulo(),
                entity.getAutor(),
                entity.getGenero(),
                entity.getAnoPublicacao(),
                entity.getUnidades(),
                entity.getUnidadesDisponiveis(),
                calcularStatus(entity));
    }

    private String calcularStatus(Livro livro) {
        if (livro.getUnidadesDisponiveis() > 0) {
            return "disponivel";
        }

        if (emprestimoRepository.existsByLivroIsbnAndDataDevolucaoIsNull(livro.getIsbn())) {
            return "emprestado";
        }

        if (!reservaRepository.findByLivroIsbnAndStatusReservaInOrderByDataReservaAsc(livro.getIsbn(), STATUS_RESERVA_ATIVOS).isEmpty()) {
            return "reservado";
        }

        return "emprestado";
    }
}
