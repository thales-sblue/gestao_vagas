package br.com.thalesdev.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora Java", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Gympass, Plano de Saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
