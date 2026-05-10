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

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private LocalDateTime dataDevolucao;

    private String status;

    private Double multa;

    public EmprestimoDto() {
    }

    public EmprestimoDto(
            Integer id,
            int matriculaUsuario,
            String isbnLivro,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            LocalDateTime dataDevolucao,
            String status,
            Double multa) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.isbnLivro = isbnLivro;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.multa = multa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
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
}
