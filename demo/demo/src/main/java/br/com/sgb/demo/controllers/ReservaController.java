package br.com.sgb.demo.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import br.com.sgb.demo.ReservaDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private List<ReservaDto> reservas = new ArrayList<>();
    private int proximoId = 1;

    public ReservaController() {
        // Dados de exemplo
        reservas.add(new ReservaDto(1, 123456, "978-85-359-0277-5", LocalDate.now().plusDays(3), 1));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listarTodos() {
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{idReserva}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable int idReserva) {
        return reservas.stream()
                .filter(r -> r.getIdReserva() == idReserva)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservaDto> criar(@Valid @RequestBody ReservaDto reservaDto) {
        reservaDto.setIdReserva(proximoId++);
        reservas.add(reservaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaDto);
    }

    @PutMapping("/{idReserva}")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable int idReserva, @Valid @RequestBody ReservaDto reservaDto) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdReserva() == idReserva) {
                reservaDto.setIdReserva(idReserva);
                reservas.set(i, reservaDto);
                return ResponseEntity.ok(reservaDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> deletar(@PathVariable int idReserva) {
        boolean removido = reservas.removeIf(r -> r.getIdReserva() == idReserva);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
