package br.com.thalesdev.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thalesdev.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.thalesdev.gestao_vagas.modules.company.entities.JobEntity;
import br.com.thalesdev.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.thalesdev.gestao_vagas.modules.company.useCases.ListAllJobsByCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/company/job")
public class JobController {

        @Autowired
        private CreateJobUseCase createJobUseCase;

        @Autowired
        private ListAllJobsByCompanyUseCase listAllJobsByCompanyUseCase;

        @PostMapping("/")
        @PreAuthorize("hasRole('COMPANY')")
        @Tag(name = "Vagas", description = "Cadastro de vagas")
        @Operation(summary = "Cadastro de vaga", description = "Função responsável por cadastrar as vagas da empresa")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = JobEntity.class))
                        })
        })
        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO CreateJobDTO,
                        HttpServletRequest request) {
                var companyId = request.getAttribute("company_id");
                try {
                        var jobEntity = JobEntity.builder()
                                        .description(CreateJobDTO.getDescription())
                                        .benefits(CreateJobDTO.getBenefits())
                                        .level(CreateJobDTO.getLevel())
                                        .companyId(UUID.fromString(companyId.toString()))
                                        .build();

                        var result = this.createJobUseCase.execute(jobEntity);
                        return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }

        @GetMapping("/")
        @PreAuthorize("hasRole('COMPANY')")
        @Tag(name = "Vagas", description = "Listagem de vagas")
        @Operation(summary = "Listagem de vagas", description = "Função responsável por listar as vagas da empresa")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = JobEntity.class))
                        })
        })
        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> listAllJobsByCompanyUseCase(HttpServletRequest request) {
                var companyId = request.getAttribute("company_id");
                var result = this.listAllJobsByCompanyUseCase.execute(UUID.fromString(companyId.toString()));
                return ResponseEntity.ok().body(result);
        }

}
