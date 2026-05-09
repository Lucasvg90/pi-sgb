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

import br.com.sgb.demo.dtos.EmprestimoDto;
import br.com.sgb.demo.services.EmprestimoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDto>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDto> buscarPorId(@PathVariable int id) {
        return emprestimoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmprestimoDto> criar(@Valid @RequestBody EmprestimoDto emprestimoDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.save(emprestimoDto));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoDto> atualizar(@PathVariable int id, @Valid @RequestBody EmprestimoDto emprestimoDto) {
        if (emprestimoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(emprestimoService.update(id, emprestimoDto));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (emprestimoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
