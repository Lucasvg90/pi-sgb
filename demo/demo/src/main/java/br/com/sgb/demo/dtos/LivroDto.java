package br.com.sgb.demo.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LivroDto {

    @NotBlank(message = "O campo ISBN é obrigatório")
    @Size(min = 1, max = 30, message = "O campo ISBN deve conter entre 1 e 30 caracteres")
    private String isbn;

    @NotBlank(message = "O campo título é obrigatório")
    @Size(min = 2, max = 200, message = "O campo título deve conter entre 2 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "O campo autor é obrigatório")
    @Size(min = 2, max = 200, message = "O campo autor deve conter entre 2 e 200 caracteres")
    private String autor;

    @NotBlank(message = "O campo gênero é obrigatório")
    @Size(min = 2, max = 100, message = "O campo gênero deve conter entre 2 e 100 caracteres")
    private String genero;
    @Min(value = 0, message = "O campo unidades deve ser no mínimo 0")
    @Max(value = 1000, message = "O campo unidades deve ser no máximo 1000")
    private int unidades;

    @Min(value = 0, message = "O campo unidades disponíveis deve ser no mínimo 0")
    @Max(value = 1000, message = "O campo unidades disponíveis deve ser no máximo 1000")
    private int unidadesDisponiveis;

    private boolean livroAtivo;

    public LivroDto() {
    }

    public LivroDto(String isbn, String titulo, String autor, String genero, int unidades, int unidadesDisponiveis, boolean livroAtivo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.unidades = unidades;
        this.unidadesDisponiveis = unidadesDisponiveis;
        this.livroAtivo = livroAtivo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getUnidadesDisponiveis() {
        return unidadesDisponiveis;
    }

    public void setUnidadesDisponiveis(int unidadesDisponiveis) {
        this.unidadesDisponiveis = unidadesDisponiveis;
    }

    public boolean isLivroAtivo() {
        return livroAtivo;
    }

    public void setLivroAtivo(boolean livroAtivo) {
        this.livroAtivo = livroAtivo;
    }

}
