package br.com.sgb.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.ReservaDto;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaDto, Integer> {
    
    List<ReservaDto> findByMatriculaUsuario(int matriculaUsuario);
    
    List<ReservaDto> findByIsbnLivro(String isbnLivro);
    
    List<ReservaDto> findByEstadoReserva(int estadoReserva);
    
    Optional<ReservaDto> findByMatriculaUsuarioAndIsbnLivro(int matriculaUsuario, String isbnLivro);
    
    List<ReservaDto> findByDataLimiteBefore(LocalDate data);
    
    List<ReservaDto> findByDataLimiteAfter(LocalDate data);
}
