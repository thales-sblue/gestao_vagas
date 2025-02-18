package br.com.thalesdev.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O campo (username) deve conter apenas letras e numeros")
    private String username;

    @Email(message = "O campo (email) deve conter um e-mail valido")
    private String email;

    @Length(min = 6, message = "O campo (password) deve conter no minimo 6 caracteres") 
    private String password;
    private String description;
    private String curriculum;
    
}
