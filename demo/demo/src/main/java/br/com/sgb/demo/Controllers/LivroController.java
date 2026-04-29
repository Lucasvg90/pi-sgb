package br.com.sgb.demo.Controllers;

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

import br.com.sgb.demo.LivroDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private List<LivroDto> livros = new ArrayList<>();

    public LivroController() {
        // Inicializando com alguns dados de exemplo
        livros.add(new LivroDto("978-85-333-0222-1", "Fundação", "Isaac Asimov", "Ficção Científica", 10, 8, true));
        livros.add(new LivroDto("978-85-359-0277-5", "Moby Dick", "Antoine de Saint-Exupéry", "Infantil", 15, 12, true));
    }

    @GetMapping
    public ResponseEntity<List<LivroDto>> listarTodos() {
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LivroDto> buscarPorIsbn(@PathVariable String isbn) {
        return livros.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroDto> criar(@Valid @RequestBody LivroDto livroDto) {
        // Verifica se já existe um livro com o mesmo ISBN
        if (livros.stream().anyMatch(l -> l.getIsbn().equals(livroDto.getIsbn()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        livros.add(livroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroDto);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<LivroDto> atualizar(@PathVariable String isbn, @Valid @RequestBody LivroDto livroDto) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getIsbn().equals(isbn)) {
                livroDto.setIsbn(isbn);
                livros.set(i, livroDto);
                return ResponseEntity.ok(livroDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletar(@PathVariable String isbn) {
        boolean removido = livros.removeIf(l -> l.getIsbn().equals(isbn));
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
