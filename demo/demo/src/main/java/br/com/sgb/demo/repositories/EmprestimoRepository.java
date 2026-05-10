package br.com.sgb.demo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.entities.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    List<Emprestimo> findByUsuarioMatricula(int matriculaUsuario);

    List<Emprestimo> findByLivroIsbn(String isbnLivro);

    boolean existsByLivroIsbnAndDataDevolucaoIsNull(String isbnLivro);

    List<Emprestimo> findByUsuarioMatriculaAndLivroIsbn(int matriculaUsuario, String isbnLivro);

    List<Emprestimo> findByDataDevolucaoBefore(LocalDateTime data);

    List<Emprestimo> findByDataDevolucaoAfter(LocalDateTime data);
}
