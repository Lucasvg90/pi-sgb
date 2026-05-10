package br.com.sgb.demo.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.entities.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuarioMatricula(int matriculaUsuario);

    List<Reserva> findByLivroIsbn(String isbnLivro);

    List<Reserva> findByStatusReserva(String statusReserva);

    List<Reserva> findByLivroIsbnAndStatusReservaInOrderByDataReservaAsc(String isbnLivro, List<String> statusReserva);

    List<Reserva> findByUsuarioMatriculaAndLivroIsbnAndStatusReservaInOrderByDataReservaAsc(
            int matriculaUsuario,
            String isbnLivro,
            List<String> statusReserva);

    Optional<Reserva> findFirstByLivroIsbnAndStatusReservaOrderByDataReservaAsc(String isbnLivro, String statusReserva);

    List<Reserva> findByDataReservaBefore(LocalDateTime data);

    List<Reserva> findByDataReservaAfter(LocalDateTime data);
}
