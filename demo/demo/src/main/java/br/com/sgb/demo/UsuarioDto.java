package br.com.sgb.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class UsuarioDto {


    @NotBlank(message = "O campo matrícula é obrigatório")
    @Max(value = 999999, message = "O campo matrícula deve ser no máximo 6 dígitos")
    private int matricula;


    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 100, message = "O campo nome deve conter entre 2 e 100 caracteres")
    private String nome;


    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O campo email deve ser um endereço de email válido")
    private String email;

    @NotBlank(message = "O campo cpf é obrigatório")
    @Size(min = 11, max = 11, message = "O campo cpf deve ter 11 dígitos")
    @Pattern(regexp = "\\d{11}", message = "O campo cpf deve conter apenas números")
    private String cpf;

    @NotBlank(message = "O campo senha é obrigatório")
    @Size(min = 6, max = 50, message = "O campo senha deve conter entre 6 e 50 caracteres")
    private String senha;

    @NotBlank(message = "O campo função do usuário é obrigatório")
    @Max(value = 3, message = "O campo função do usuário deve ser no máximo 3")
    private int funcao_usuario;

    @NotBlank(message = "O campo endereço é obrigatório")
    @Size(min = 5, max = 200, message = "O campo endereço deve conter entre 5 e 200 caracteres")
    private String endereco; // Mudar para endereço completo depois;

    public UsuarioDto() {
    }

    public UsuarioDto(int matricula, String nome, String email, String cpf, String senha, int funcao_usuario,
            String endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.funcao_usuario = funcao_usuario;
        this.endereco = endereco;

    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getFuncao_usuario() {
        return funcao_usuario;
    }

    public void setFuncao_usuario(int funcao_usuario) {
        this.funcao_usuario = funcao_usuario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
