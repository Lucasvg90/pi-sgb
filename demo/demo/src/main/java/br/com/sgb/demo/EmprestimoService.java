package br.com.sgb.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.repositories.EmprestimoRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<EmprestimoDto> findAll() {
        return repository.findAll();
    }

    public Optional<EmprestimoDto> findById(int id) {
        return repository.findById(id);
    }

    public List<EmprestimoDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByMatriculaUsuario(matriculaUsuario);
    }

    public List<EmprestimoDto> findByIsbnLivro(String isbn) {
        return repository.findByIsbnLivro(isbn);
    }

    public List<EmprestimoDto> findByFlagAtrasado(boolean atrasado) {
        return repository.findByFlagAtrasado(atrasado);
    }

    public Optional<EmprestimoDto> findByMatriculaAndIsbn(int matriculaUsuario, String isbnLivro) {
        return repository.findByMatriculaUsuarioAndIsbnLivro(matriculaUsuario, isbnLivro);
    }

    public List<EmprestimoDto> findByDataDevolucaoBefore(LocalDate data) {
        return repository.findByDataDevolucaoBefore(data);
    }

    public List<EmprestimoDto> findByDataDevolucaoAfter(LocalDate data) {
        return repository.findByDataDevolucaoAfter(data);
    }

    public EmprestimoDto save(EmprestimoDto dto) {
        return repository.save(dto);
    }

    public EmprestimoDto update(int id, EmprestimoDto dto) {
        dto.setId(id);
        return repository.save(dto);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}