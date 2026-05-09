package br.com.sgb.demo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.entities.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuarioMatricula(int matriculaUsuario);

    List<Reserva> findByLivroIsbn(String isbnLivro);

    List<Reserva> findByEstadoReserva(int estadoReserva);

    List<Reserva> findByUsuarioMatriculaAndLivroIsbn(int matriculaUsuario, String isbnLivro);

    List<Reserva> findByDataLimiteBefore(LocalDateTime data);

    List<Reserva> findByDataLimiteAfter(LocalDateTime data);
}
