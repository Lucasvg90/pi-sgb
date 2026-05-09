package br.com.sgb.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<ReservaDto>> listarTodos() {
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
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reservaDto));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idReserva}")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable int idReserva, @Valid @RequestBody ReservaDto reservaDto) {
        if (reservaService.findById(idReserva).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(reservaService.update(idReserva, reservaDto));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> deletar(@PathVariable int idReserva) {
        if (reservaService.findById(idReserva).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservaService.delete(idReserva);
        return ResponseEntity.noContent().build();
    }
}
