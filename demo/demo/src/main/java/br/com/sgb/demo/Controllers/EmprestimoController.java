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

import br.com.sgb.demo.EmprestimoDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private List<EmprestimoDto> emprestimos = new ArrayList<>();
    private int proximoId = 1;

    public EmprestimoController() {
        // Dados de exemplo
        emprestimos.add(new EmprestimoDto(1, 123456, "978-85-333-0222-1", LocalDate.now().minusDays(5), LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), false));
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDto>> listarTodos() {
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDto> buscarPorId(@PathVariable int id) {
        return emprestimos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmprestimoDto> criar(@Valid @RequestBody EmprestimoDto emprestimoDto) {
        emprestimoDto.setId(proximoId++);
        emprestimos.add(emprestimoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoDto> atualizar(@PathVariable int id, @Valid @RequestBody EmprestimoDto emprestimoDto) {
        for (int i = 0; i < emprestimos.size(); i++) {
            if (emprestimos.get(i).getId() == id) {
                emprestimoDto.setId(id);
                emprestimos.set(i, emprestimoDto);
                return ResponseEntity.ok(emprestimoDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        boolean removido = emprestimos.removeIf(e -> e.getId() == id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
