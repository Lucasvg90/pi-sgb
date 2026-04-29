package br.com.sgb.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgb.demo.LivroDto;

@Repository
public interface LivroRepository extends JpaRepository<LivroDto, String> {
    
    Optional<LivroDto> findByIsbn(String isbn);
    
    List<LivroDto> findByTituloContainingIgnoreCase(String titulo);
    
    List<LivroDto> findByAutorContainingIgnoreCase(String autor);
    
    List<LivroDto> findByGeneroContainingIgnoreCase(String genero);
    
    List<LivroDto> findByLivroAtivo(boolean livroAtivo);
} 
