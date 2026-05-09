package br.com.sgb.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @Column(name = "isbn_pk", nullable = false, updatable = false, length = 30)
    private String isbn;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 200)
    private String autor;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(nullable = false)
    private Integer unidades;

    @Column(name = "unidades_disponiveis", nullable = false)
    private Integer unidadesDisponiveis;

    @Column(name = "livro_ativo", nullable = false)
    private boolean livroAtivo;

    @OneToMany(mappedBy = "livro")
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos = new ArrayList<>();

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

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Integer getUnidadesDisponiveis() {
        return unidadesDisponiveis;
    }

    public void setUnidadesDisponiveis(Integer unidadesDisponiveis) {
        this.unidadesDisponiveis = unidadesDisponiveis;
    }

    public boolean isLivroAtivo() {
        return livroAtivo;
    }

    public void setLivroAtivo(boolean livroAtivo) {
        this.livroAtivo = livroAtivo;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
