package br.com.sgb.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDto {

    private Integer matricula;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 500, message = "O campo nome deve conter entre 2 e 500 caracteres")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O campo email deve ser um endereço de email válido")
    @Size(max = 100, message = "O campo email deve conter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "O campo cpf é obrigatório")
    @Size(min = 11, max = 30, message = "O campo cpf deve conter entre 11 e 30 caracteres")
    @Pattern(regexp = "(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "O campo cpf deve estar no formato 00000000000 ou 000.000.000-00")
    private String cpf;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Size(min = 10, max = 20, message = "O campo telefone deve conter entre 10 e 20 caracteres")
    private String telefone;

    @Min(value = 1, message = "O campo função do usuário deve ser no mínimo 1")
    @Max(value = 3, message = "O campo função do usuário deve ser no máximo 3")
    private int funcaoUsuario;

    private boolean ativo;

    public UsuarioDto() {
    }

    public UsuarioDto(
            Integer matricula,
            String nome,
            String email,
            String cpf,
            String telefone,
            int funcaoUsuario,
            boolean ativo) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.funcaoUsuario = funcaoUsuario;
        this.ativo = ativo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getFuncaoUsuario() {
        return funcaoUsuario;
    }

    public void setFuncaoUsuario(int funcaoUsuario) {
        this.funcaoUsuario = funcaoUsuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
