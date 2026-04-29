package br.com.sgb.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ReservaDto {

    
    private int idReserva;
    
    @NotBlank
    @Min(value = 1, message = "O campo matrícula do usuário deve ser no mínimo 1")
    private int matriculaUsuario;

    @NotBlank
    private String isbnLivro;

    @NotBlank
    @Future(message = "Necessária uma data futura")
    private LocalDate dataLimite;

    @Positive
    private int estadoReserva;

    
    

    public ReservaDto(int idReserva, int matriculaUsuario, String isbnLivro, LocalDate dataLimite, int estadoReserva) {
        this.idReserva = idReserva;
        this.matriculaUsuario = matriculaUsuario;
        this.isbnLivro = isbnLivro;
        this.dataLimite = dataLimite;
        this.estadoReserva = estadoReserva;
    }

    public ReservaDto() {
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
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
