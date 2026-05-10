package br.com.sgb.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgb.demo.dtos.ReservaDto;
import br.com.sgb.demo.services.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listarTodos(@RequestParam(required = false) Integer matriculaUsuario) {
        if (matriculaUsuario != null) {
            return ResponseEntity.ok(reservaService.findByMatriculaUsuario(matriculaUsuario));
        }
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{idReserva}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable int idReserva) {
        return reservaService.findById(idReserva)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservaDto> criar(@Valid @RequestBody ReservaDto reservaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reservaDto));
    }

    @PatchMapping("/{idReserva}/cancelamento")
    public ResponseEntity<ReservaDto> cancelar(@PathVariable int idReserva) {
        return ResponseEntity.ok(reservaService.cancelar(idReserva));
    }
}
