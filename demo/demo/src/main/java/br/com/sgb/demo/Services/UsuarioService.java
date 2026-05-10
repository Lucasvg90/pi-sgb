package br.com.sgb.demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgb.demo.dtos.UsuarioDto;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDto> findByMatricula(int matricula) {
        return repository.findByMatricula(matricula).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDto> findByCpf(String cpf) {
        return repository.findByCpf(cpf).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDto> findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> findByNomeContaining(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> findByAtivo(boolean ativo) {
        return repository.findByAtivo(ativo).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> findByFuncaoUsuario(int funcao) {
        return repository.findByFuncaoUsuario(funcao).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDto save(UsuarioDto dto) {
        validarDuplicidade(dto, null);
        Usuario entity = new Usuario();
        preencherCampos(entity, dto);
        entity.setSenha(gerarSenhaInicial(dto.getCpf()));
        return toDto(repository.save(entity));
    }

    @Transactional
    public UsuarioDto update(int matricula, UsuarioDto dto) {
        Usuario entity = repository.findByMatricula(matricula)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        validarDuplicidade(dto, matricula);
        preencherCampos(entity, dto);
        return toDto(repository.save(entity));
    }

    @Transactional
    public void delete(int matricula) {
        Usuario entity = repository.findByMatricula(matricula)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        repository.delete(entity);
    }

    private void validarDuplicidade(UsuarioDto dto, Integer matriculaAtual) {
        repository.findByEmailIgnoreCase(dto.getEmail())
                .filter(usuario -> matriculaAtual == null || !usuario.getMatricula().equals(matriculaAtual))
                .ifPresent(usuario -> {
                    throw new IllegalStateException("Já existe um usuário com este email");
                });

        repository.findByCpf(dto.getCpf())
                .filter(usuario -> matriculaAtual == null || !usuario.getMatricula().equals(matriculaAtual))
                .ifPresent(usuario -> {
                    throw new IllegalStateException("Já existe um usuário com este CPF");
                });
    }

    private void preencherCampos(Usuario entity, UsuarioDto dto) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setFuncaoUsuario(dto.getFuncaoUsuario());
        entity.setAtivo(dto.isAtivo());
    }

    private String gerarSenhaInicial(String cpf) {
        String cpfNumerico = cpf.replaceAll("\\D", "");
        return cpfNumerico.isBlank() ? "123456" : cpfNumerico;
    }

    private UsuarioDto toDto(Usuario entity) {
        return new UsuarioDto(
                entity.getMatricula(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getTelefone(),
                entity.getFuncaoUsuario(),
                entity.isAtivo());
    }
}
