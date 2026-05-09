package br.com.sgb.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.dtos.EmprestimoDto;
import br.com.sgb.demo.entities.Emprestimo;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.EmprestimoRepository;
import br.com.sgb.demo.repositories.LivroRepository;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository repository, UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public List<EmprestimoDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<EmprestimoDto> findById(int id) {
        return repository.findById(id).map(this::toDto);
    }

    public List<EmprestimoDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByUsuarioMatricula(matriculaUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmprestimoDto> findByIsbnLivro(String isbn) {
        return repository.findByLivroIsbn(isbn).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmprestimoDto> findByFlagAtrasado(boolean atrasado) {
        return repository.findByFlagAtrasado(atrasado).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmprestimoDto> findByMatriculaAndIsbn(int matriculaUsuario, String isbnLivro) {
        return repository.findByUsuarioMatriculaAndLivroIsbn(matriculaUsuario, isbnLivro).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmprestimoDto> findByDataDevolucaoBefore(LocalDateTime data) {
        return repository.findByDataDevolucaoBefore(data).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmprestimoDto> findByDataDevolucaoAfter(LocalDateTime data) {
        return repository.findByDataDevolucaoAfter(data).stream().map(this::toDto).collect(Collectors.toList());
    }

    public EmprestimoDto save(EmprestimoDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    public EmprestimoDto update(int id, EmprestimoDto dto) {
        repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Emprestimo nao encontrado"));
        dto.setId(id);
        return toDto(repository.save(toEntity(dto)));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    private EmprestimoDto toDto(Emprestimo entity) {
        return new EmprestimoDto(
                entity.getId(),
                entity.getUsuario().getMatricula(),
                entity.getLivro().getIsbn(),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getDataDevolucao(),
                entity.isFlagAtrasado());
    }

    private Emprestimo toEntity(EmprestimoDto dto) {
        Usuario usuario = usuarioRepository.findByMatricula(dto.getMatriculaUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuario nao encontrado"));
        Livro livro = livroRepository.findByIsbn(dto.getIsbnLivro())
                .orElseThrow(() -> new NoSuchElementException("Livro nao encontrado"));

        Emprestimo entity = new Emprestimo();
        if (dto.getId() != null && dto.getId() > 0) {
            entity.setId(dto.getId());
        }
        entity.setUsuario(usuario);
        entity.setLivro(livro);
        entity.setDataInicio(dto.getData_inicio());
        entity.setDataFim(dto.getData_fim());
        entity.setDataDevolucao(dto.getData_devolucao());
        entity.setFlagAtrasado(dto.isFlagAtrasado());
        return entity;
    }
}
