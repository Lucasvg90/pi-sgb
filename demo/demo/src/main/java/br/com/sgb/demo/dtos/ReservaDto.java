package br.com.sgb.demo.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ReservaDto {

    private Integer idReserva;

    @Min(value = 1, message = "O campo matrícula do usuário deve ser no mínimo 1")
    private int matriculaUsuario;

    @NotBlank
    private String isbnLivro;

    private LocalDateTime dataLimite;

    @Positive
    private int estadoReserva;

    public ReservaDto(Integer idReserva, int matriculaUsuario, String isbnLivro, LocalDateTime dataLimite, int estadoReserva) {
        this.idReserva = idReserva;
        this.matriculaUsuario = matriculaUsuario;
        this.isbnLivro = isbnLivro;
        this.dataLimite = dataLimite;
        this.estadoReserva = estadoReserva;
    }

    public ReservaDto() {
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    public int getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(int estadoReserva) {
        this.estadoReserva = estadoReserva;
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
