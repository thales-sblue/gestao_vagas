package br.com.thalesdev.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "João da Silva", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O campo (username) deve conter apenas letras e numeros")
    @Schema(example = "joaosilva", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo (email) deve conter um e-mail valido")
    @Schema(example = "joao@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 6, message = "O campo (password) deve conter no minimo 6 caracteres")
    @Schema(example = "123456", minLength = 6, requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Schema(example = "Desenvolvedor Java Pleno", requiredMode = RequiredMode.REQUIRED)
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
