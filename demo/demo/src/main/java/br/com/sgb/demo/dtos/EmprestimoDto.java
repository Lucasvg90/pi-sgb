package br.com.sgb.demo.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmprestimoDto {

    private Integer id;

    @Min(value = 1, message = "O campo matrícula do usuário deve ser no mínimo 1")
    private int matriculaUsuario;

    @NotBlank
    private String isbnLivro;

    private LocalDateTime data_inicio;

    private LocalDateTime data_fim;

    private LocalDateTime data_devolucao;

    private boolean flagAtrasado;

    public EmprestimoDto() {
    }

    public EmprestimoDto(Integer id, int matriculaUsuario, String isbnLivro, LocalDateTime data_inicio, LocalDateTime data_fim, LocalDateTime data_devolucao, boolean flagAtrasado) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.isbnLivro = isbnLivro;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_devolucao = data_devolucao;
        this.flagAtrasado = flagAtrasado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDateTime data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDateTime getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDateTime data_fim) {
        this.data_fim = data_fim;
    }

    public LocalDateTime getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(LocalDateTime data_devolucao) {
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
