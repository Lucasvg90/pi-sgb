package br.com.sgb.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.repositories.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<LivroDto> findAll() {
        return repository.findAll();
    }

    public Optional<LivroDto> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public List<LivroDto> findByTituloContaining(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<LivroDto> findByAutorContaining(String autor) {
        return repository.findByAutorContainingIgnoreCase(autor);
    }

    public List<LivroDto> findByGeneroContaining(String genero) {
        return repository.findByGeneroContainingIgnoreCase(genero);
    }

    public List<LivroDto> findByLivroAtivo(boolean ativo) {
        return repository.findByLivroAtivo(ativo);
    }

    public LivroDto save(LivroDto dto) {
        return repository.save(dto);
    }

    public LivroDto update(String isbn, LivroDto dto) {
        dto.setIsbn(isbn);
        return repository.save(dto);
    }

    public void delete(String isbn) {
        repository.findByIsbn(isbn).ifPresent(repository::delete);
    }
}