package br.com.sgb.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByMatricula(int matricula);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailIgnoreCase(String email);

    Optional<Usuario> findByEmailIgnoreCaseAndAtivoTrue(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    List<Usuario> findByAtivo(boolean ativo);

    List<Usuario> findByFuncaoUsuario(int funcaoUsuario);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCpf(String cpf);
}
