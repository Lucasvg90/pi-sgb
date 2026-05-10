package br.com.sgb.demo.controllers;

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

import br.com.sgb.demo.dtos.LivroDto;
import br.com.sgb.demo.services.LivroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroDto>> listarTodos() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LivroDto> buscarPorIsbn(@PathVariable String isbn) {
        return livroService.findByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroDto> criar(@Valid @RequestBody LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroDto));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<LivroDto> atualizar(@PathVariable String isbn, @Valid @RequestBody LivroDto livroDto) {
        return ResponseEntity.ok(livroService.update(isbn, livroDto));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletar(@PathVariable String isbn) {
        livroService.delete(isbn);
        return ResponseEntity.noContent().build();
    }
}
