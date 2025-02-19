package br.com.thalesdev.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "company")
@Data
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

     @NotBlank()
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O campo (username) deve conter apenas letras e numeros")
    private String username;

    @Email(message = "O campo (email) deve conter um e-mail valido")
    private String email;

    @Length(min = 6, message = "O campo (password) deve conter no minimo 6 caracteres") 
    private String password;

    private String website;
    private String name;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
