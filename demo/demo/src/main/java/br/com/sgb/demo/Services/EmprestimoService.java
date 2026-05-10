package br.com.sgb.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgb.demo.dtos.EmprestimoDto;
import br.com.sgb.demo.entities.Emprestimo;
import br.com.sgb.demo.entities.Livro;
import br.com.sgb.demo.entities.Reserva;
import br.com.sgb.demo.entities.Usuario;
import br.com.sgb.demo.repositories.EmprestimoRepository;
import br.com.sgb.demo.repositories.LivroRepository;
import br.com.sgb.demo.repositories.ReservaRepository;
import br.com.sgb.demo.repositories.UsuarioRepository;

@Service
public class EmprestimoService {

    private static final List<String> STATUS_RESERVA_ATIVOS = List.of("ativa", "disponivel");

    private final EmprestimoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final ReservaRepository reservaRepository;

    public EmprestimoService(
            EmprestimoRepository repository,
            UsuarioRepository usuarioRepository,
            LivroRepository livroRepository,
            ReservaRepository reservaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<EmprestimoDto> findById(int id) {
        return repository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDto> findByMatriculaUsuario(int matriculaUsuario) {
        return repository.findByUsuarioMatricula(matriculaUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public EmprestimoDto save(EmprestimoDto dto) {
        Usuario usuario = usuarioRepository.findByMatricula(dto.getMatriculaUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Livro livro = livroRepository.findByIsbn(dto.getIsbnLivro())
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado"));

        validarUsuarioParaEmprestimo(usuario);

        if (livro.getUnidadesDisponiveis() <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis para empréstimo");
        }

        Emprestimo entity = new Emprestimo();
        entity.setUsuario(usuario);
        entity.setLivro(livro);
        entity.setDataInicio(dto.getDataInicio() != null ? dto.getDataInicio() : LocalDateTime.now());
        entity.setDataFim(dto.getDataFim() != null ? dto.getDataFim() : LocalDateTime.now().plusDays(14));
        entity.setDataDevolucao(null);

        livro.setUnidadesDisponiveis(livro.getUnidadesDisponiveis() - 1);
        livroRepository.save(livro);

        return toDto(repository.save(entity));
    }

    @Transactional
    public EmprestimoDto registrarDevolucao(int id) {
        Emprestimo emprestimo = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucao() != null) {
            return toDto(emprestimo);
        }

        emprestimo.setDataDevolucao(LocalDateTime.now());

        Livro livro = emprestimo.getLivro();
        Optional<Reserva> proximaReserva = reservaRepository.findFirstByLivroIsbnAndStatusReservaOrderByDataReservaAsc(
                livro.getIsbn(),
                "ativa");

        if (proximaReserva.isPresent()) {
            Reserva reserva = proximaReserva.get();
            reserva.setStatusReserva("disponivel");
            reservaRepository.save(reserva);
        } else {
            livro.setUnidadesDisponiveis(Math.min(livro.getUnidades(), livro.getUnidadesDisponiveis() + 1));
            livroRepository.save(livro);
        }

        return toDto(repository.save(emprestimo));
    }

    private void validarUsuarioParaEmprestimo(Usuario usuario) {
        if (!usuario.isAtivo()) {
            throw new IllegalStateException("Usuário inativo não pode realizar empréstimos");
        }

        if (usuario.getFuncaoUsuario() != 1) {
            throw new IllegalStateException("Somente leitores podem receber empréstimos");
        }
    }

    private EmprestimoDto toDto(Emprestimo entity) {
        return new EmprestimoDto(
                entity.getId(),
                entity.getUsuario().getMatricula(),
                entity.getLivro().getIsbn(),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getDataDevolucao(),
                calcularStatus(entity),
                calcularMulta(entity));
    }

    private String calcularStatus(Emprestimo entity) {
        if (entity.getDataDevolucao() != null) {
            return "devolvido";
        }

        if (entity.getDataFim().isBefore(LocalDateTime.now())) {
            return "atrasado";
        }

        return "ativo";
    }

    private Double calcularMulta(Emprestimo entity) {
        LocalDate dataReferencia = entity.getDataDevolucao() != null
                ? entity.getDataDevolucao().toLocalDate()
                : LocalDate.now();
        LocalDate dataFim = entity.getDataFim().toLocalDate();
        long diasAtraso = ChronoUnit.DAYS.between(dataFim, dataReferencia);
        return diasAtraso > 0 ? diasAtraso * 3.0 : 0.0;
    }
}
