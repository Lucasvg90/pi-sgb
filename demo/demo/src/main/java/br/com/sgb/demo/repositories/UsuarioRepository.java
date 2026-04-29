package br.com.sgb.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.UsuarioDto;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDto, Integer> {
    
    Optional<UsuarioDto> findByMatricula(int matricula);
    
    Optional<UsuarioDto> findByCpf(String cpf);
    
    Optional<UsuarioDto> findByEmail(String email);
    
    List<UsuarioDto> findByNomeContainingIgnoreCase(String nome);
    
    List<UsuarioDto> findByAtivo(boolean ativo);
    
    List<UsuarioDto> findByFuncaoUsuario(int funcaoUsuario);
}
