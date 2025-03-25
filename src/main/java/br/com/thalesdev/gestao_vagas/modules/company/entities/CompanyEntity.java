package br.com.thalesdev.gestao_vagas.modules.company.entities;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O campo (username) deve conter apenas letras e numeros")
    @Schema(example = "solartecno", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo (email) deve conter um e-mail valido")
    @Schema(example = "solartecno@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 6, message = "O campo (password) deve conter no minimo 6 caracteres")
    @Schema(example = "123456", requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Schema(example = "solartecno.com", requiredMode = RequiredMode.REQUIRED)
    private String website;

    @Schema(example = "Solar Tecno", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "Empresa de energia solar", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
