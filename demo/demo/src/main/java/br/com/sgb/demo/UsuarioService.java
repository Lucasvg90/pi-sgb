package br.com.sgb.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDto> findAll() {
        return repository.findAll();
    }

    public Optional<UsuarioDto> findByMatricula(int matricula) {
        return repository.findByMatricula(matricula);
    }

    public Optional<UsuarioDto> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<UsuarioDto> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<UsuarioDto> findByNomeContaining(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<UsuarioDto> findByAtivo(boolean ativo) {
        return repository.findByAtivo(ativo);
    }

    public List<UsuarioDto> findByFuncaoUsuario(int funcao) {
        return repository.findByFuncaoUsuario(funcao);
    }

    public UsuarioDto save(UsuarioDto dto) {
        return repository.save(dto);
    }

    public UsuarioDto update(int matricula, UsuarioDto dto) {
        dto.setMatricula(matricula);
        return repository.save(dto);
    }

    public void delete(int matricula) {
        repository.findByMatricula(matricula).ifPresent(repository::delete);
    }
}