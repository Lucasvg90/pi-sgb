package br.com.sgb.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgb.demo.dtos.ReservaDto;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.entities.Reserva;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.LivroRepository;
import br.com.sgb.demo.repositories.ReservaRepository;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class ReservaService {

    private static final List<String> STATUS_ATIVOS = List.of("ativa", "disponivel");

    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public ReservaService(ReservaRepository repository, UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservaDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ReservaDto> findById(int id) {
        return repository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ReservaDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByUsuarioMatricula(matriculaUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ReservaDto save(ReservaDto dto) {
        Usuario usuario = usuarioRepository.findByMatricula(dto.getMatriculaUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Livro livro = livroRepository.findByIsbn(dto.getIsbnLivro())
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado"));

        validarUsuarioParaReserva(usuario);
        validarLivroParaReserva(dto, livro);

        Reserva entity = new Reserva();
        entity.setUsuario(usuario);
        entity.setLivro(livro);
        entity.setDataReserva(dto.getDataReserva() != null ? dto.getDataReserva() : LocalDateTime.now());
        entity.setStatusReserva("ativa");

        return toDto(repository.save(entity));
    }

    @Transactional
    public ReservaDto cancelar(int idReserva) {
        Reserva reserva = repository.findById(idReserva)
                .orElseThrow(() -> new NoSuchElementException("Reserva não encontrada"));

        if (!STATUS_ATIVOS.contains(reserva.getStatusReserva())) {
            return toDto(reserva);
        }

        if ("disponivel".equals(reserva.getStatusReserva())) {
            liberarCopiaReservada(reserva);
        }

        reserva.setStatusReserva("cancelada");
        return toDto(repository.save(reserva));
    }

    private void validarUsuarioParaReserva(Usuario usuario) {
        if (!usuario.isAtivo()) {
            throw new IllegalStateException("Usuário inativo não pode reservar livros");
        }

        if (usuario.getFuncaoUsuario() != 1) {
            throw new IllegalStateException("Somente leitores podem reservar livros");
        }
    }

    private void validarLivroParaReserva(ReservaDto dto, Livro livro) {
        if (livro.getUnidadesDisponiveis() > 0) {
            throw new IllegalStateException("O livro está disponível para empréstimo e não precisa de reserva");
        }

        boolean reservaDuplicada = !repository.findByUsuarioMatriculaAndLivroIsbnAndStatusReservaInOrderByDataReservaAsc(
                dto.getMatriculaUsuario(),
                dto.getIsbnLivro(),
                STATUS_ATIVOS).isEmpty();

        if (reservaDuplicada) {
            throw new IllegalStateException("Já existe uma reserva ativa para este usuário e livro");
        }
    }

    private void liberarCopiaReservada(Reserva reservaAtual) {
        List<Reserva> filaAtiva = repository.findByLivroIsbnAndStatusReservaInOrderByDataReservaAsc(
                reservaAtual.getLivro().getIsbn(),
                List.of("ativa"));

        if (!filaAtiva.isEmpty()) {
            Reserva proximaReserva = filaAtiva.get(0);
            proximaReserva.setStatusReserva("disponivel");
            repository.save(proximaReserva);
            return;
        }

        Livro livro = reservaAtual.getLivro();
        livro.setUnidadesDisponiveis(Math.min(livro.getUnidades(), livro.getUnidadesDisponiveis() + 1));
        livroRepository.save(livro);
    }

    private ReservaDto toDto(Reserva entity) {
        return new ReservaDto(
                entity.getIdReserva(),
                entity.getUsuario().getMatricula(),
                entity.getLivro().getIsbn(),
                entity.getDataReserva(),
                entity.getStatusReserva(),
                calcularPosicao(entity));
    }

    private Integer calcularPosicao(Reserva entity) {
        if (!STATUS_ATIVOS.contains(entity.getStatusReserva())) {
            return 0;
        }

        List<Reserva> fila = repository.findByLivroIsbnAndStatusReservaInOrderByDataReservaAsc(
                entity.getLivro().getIsbn(),
                STATUS_ATIVOS);

        for (int i = 0; i < fila.size(); i++) {
            if (fila.get(i).getIdReserva().equals(entity.getIdReserva())) {
                return i + 1;
            }
        }

        return fila.size() + 1;
    }
}
