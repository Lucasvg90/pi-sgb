package br.com.sgb.demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgb.demo.dtos.UsuarioDto;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<UsuarioDto> findByMatricula(int matricula) {
        return repository.findByMatricula(matricula).map(this::toDto);
    }

    public Optional<UsuarioDto> findByCpf(String cpf) {
        return repository.findByCpf(cpf).map(this::toDto);
    }

    public Optional<UsuarioDto> findByEmail(String email) {
        return repository.findFirstByEmailIgnoreCase(email).map(this::toDto);
    }

    public List<UsuarioDto> findByNomeContaining(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<UsuarioDto> findByAtivo(boolean ativo) {
        return repository.findByAtivo(ativo).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<UsuarioDto> findByFuncaoUsuario(int funcao) {
        return repository.findByFuncaoUsuario(funcao).stream().map(this::toDto).collect(Collectors.toList());
    }

    public UsuarioDto save(UsuarioDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    public UsuarioDto update(int matricula, UsuarioDto dto) {
        repository.findByMatricula(matricula)
                .orElseThrow(() -> new NoSuchElementException("Usuario nao encontrado"));
        dto.setMatricula(matricula);
        return toDto(repository.save(toEntity(dto)));
    }

    public void delete(int matricula) {
        repository.deleteById(matricula);
    }

    private UsuarioDto toDto(Usuario entity) {
        return new UsuarioDto(
                entity.getMatricula(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getSenha(),
                entity.getFuncaoUsuario(),
                entity.getRua(),
                entity.getNumero(),
                entity.getCep(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.isAtivo());
    }

    private Usuario toEntity(UsuarioDto dto) {
        Usuario entity = new Usuario();
        if (dto.getMatricula() != null && dto.getMatricula() > 0) {
            entity.setMatricula(dto.getMatricula());
        }
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setSenha(dto.getSenha());
        entity.setFuncaoUsuario(dto.getFuncao_usuario());
        entity.setRua(dto.getRua());
        entity.setNumero(dto.getNumero());
        entity.setCep(dto.getCep());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setAtivo(dto.isAtivo());
        return entity;
    }
}
