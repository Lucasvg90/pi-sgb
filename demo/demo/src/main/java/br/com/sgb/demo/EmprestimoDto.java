package br.com.sgb.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


public class EmprestimoDto {

    private int id;

    @NotBlank
    @Min(value = 1, message = "O campo matrícula do usuário deve ser no mínimo 1")
    private int matriculaUsuario;
    
    @NotBlank
    private String isbnLivro;
    
    @NotNull
	@PastOrPresent(message = "O campo data de início deve ser uma data passada ou presente")
    private LocalDate data_inicio;

    @NotNull
    @Future(message = "O campo data de fim deve ser uma data futura")
    private LocalDate data_fim;

   @NotNull
   @Future(message = "O campo data de devolução deve ser uma data futura")
   private LocalDate data_devolucao;

   @NotNull
   private boolean flagAtrasado;

    public EmprestimoDto() {
    }

    public EmprestimoDto(int id, int matriculaUsuario, String isbnLivro, LocalDate data_inicio, LocalDate data_fim, LocalDate data_devolucao, boolean flagAtrasado) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.isbnLivro = isbnLivro;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_devolucao = data_devolucao;
        this.flagAtrasado = flagAtrasado;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getData_inicio() {
        return data_inicio;
    }
    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }
    public LocalDate getData_fim() {
        return data_fim;
    }
    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }
    public LocalDate getData_devolucao() {
        return data_devolucao;
    }
    public void setData_devolucao(LocalDate data_devolucao) {
        this.data_devolucao = data_devolucao;
    }
    public boolean isFlagAtrasado() {
        return flagAtrasado;
    }

    public int getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(int matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }

    public String getIsbnLivro() {
        return isbnLivro;
    }

    public void setIsbnLivro(String isbnLivro) {
        this.isbnLivro = isbnLivro;
    }

    public void setFlagAtrasado(boolean flagAtrasado) {
        this.flagAtrasado = flagAtrasado;
    }

    
    



}
