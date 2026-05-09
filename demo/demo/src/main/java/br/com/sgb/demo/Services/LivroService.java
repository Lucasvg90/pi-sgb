package br.com.sgb.demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.dtos.LivroDto;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.repositories.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<LivroDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<LivroDto> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn).map(this::toDto);
    }

    public List<LivroDto> findByTituloContaining(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<LivroDto> findByAutorContaining(String autor) {
        return repository.findByAutorContainingIgnoreCase(autor).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<LivroDto> findByGeneroContaining(String genero) {
        return repository.findByGeneroContainingIgnoreCase(genero).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<LivroDto> findByLivroAtivo(boolean ativo) {
        return repository.findByLivroAtivo(ativo).stream().map(this::toDto).collect(Collectors.toList());
    }

    public LivroDto save(LivroDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    public LivroDto update(String isbn, LivroDto dto) {
        repository.findByIsbn(isbn)
                .orElseThrow(() -> new NoSuchElementException("Livro nao encontrado"));
        dto.setIsbn(isbn);
        return toDto(repository.save(toEntity(dto)));
    }

    public void delete(String isbn) {
        repository.deleteById(isbn);
    }

    private LivroDto toDto(Livro entity) {
        return new LivroDto(
                entity.getIsbn(),
                entity.getTitulo(),
                entity.getAutor(),
                entity.getGenero(),
                entity.getUnidades(),
                entity.getUnidadesDisponiveis(),
                entity.isLivroAtivo());
    }

    private Livro toEntity(LivroDto dto) {
        Livro entity = new Livro();
        entity.setIsbn(dto.getIsbn());
        entity.setTitulo(dto.getTitulo());
        entity.setAutor(dto.getAutor());
        entity.setGenero(dto.getGenero());
        entity.setUnidades(dto.getUnidades());
        entity.setUnidadesDisponiveis(dto.getUnidadesDisponiveis());
        entity.setLivroAtivo(dto.isLivroAtivo());
        return entity;
    }
}
