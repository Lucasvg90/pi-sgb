package br.com.sgb.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgb.demo.dtos.LoginRequestDto;
import br.com.sgb.demo.dtos.UsuarioDto;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public UsuarioDto login(LoginRequestDto loginRequestDto) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCaseAndAtivoTrue(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!usuario.getSenha().equals(loginRequestDto.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        return new UsuarioDto(
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getFuncaoUsuario(),
                usuario.isAtivo());
    }
}
