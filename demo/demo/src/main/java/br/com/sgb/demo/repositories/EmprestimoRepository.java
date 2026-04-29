package br.com.sgb.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.EmprestimoDto;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoDto, Integer> {
    
    List<EmprestimoDto> findByMatriculaUsuario(int matriculaUsuario);
    
    List<EmprestimoDto> findByIsbnLivro(String isbnLivro);
    
    List<EmprestimoDto> findByFlagAtrasado(boolean flagAtrasado);
    
    Optional<EmprestimoDto> findByMatriculaUsuarioAndIsbnLivro(int matriculaUsuario, String isbnLivro);
    
    List<EmprestimoDto> findByDataDevolucaoBefore(LocalDate data);
    
    List<EmprestimoDto> findByDataDevolucaoAfter(LocalDate data);
}
