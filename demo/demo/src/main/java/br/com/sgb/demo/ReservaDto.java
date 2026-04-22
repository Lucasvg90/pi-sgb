package br.com.sgb.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public class ReservaDto {

    
    private int idReserva;

    @NotBlank
    @Future(message = "Necessária uma data futura")
    private LocalDate dataLimite;

    @NotBlank
    private int estadoReserva;

    
    public ReservaDto(int idReserva, @NotBlank @Future(message = "Necessária uma data futura") LocalDate dataLimite,
            @NotBlank int estadoReserva) {
        this.idReserva = idReserva;
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


    

}
