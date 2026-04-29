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

import br.com.sgb.demo.UsuarioDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private List<UsuarioDto> usuarios = new ArrayList<>();

    public UsuarioController() {
        // Dados de exemplo
        usuarios.add(new UsuarioDto(123456, "Takakara Nomuro", "taka@email.com", "12345678901", "senha123", 1, "Rua das Flores", "100", "01234-567", "Centro", "São Paulo", "SP", true));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarTodos() {
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<UsuarioDto> buscarPorMatricula(@PathVariable int matricula) {
        return usuarios.stream()
                .filter(u -> u.getMatricula() == matricula)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@Valid @RequestBody UsuarioDto usuarioDto) {
        if (usuarios.stream().anyMatch(u -> u.getMatricula() == usuarioDto.getMatricula())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        usuarios.add(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable int matricula, @Valid @RequestBody UsuarioDto usuarioDto) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getMatricula() == matricula) {
                usuarioDto.setMatricula(matricula);
                usuarios.set(i, usuarioDto);
                return ResponseEntity.ok(usuarioDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable int matricula) {
        boolean removido = usuarios.removeIf(u -> u.getMatricula() == matricula);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
