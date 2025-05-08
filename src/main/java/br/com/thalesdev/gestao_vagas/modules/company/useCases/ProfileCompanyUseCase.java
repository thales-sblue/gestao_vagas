package br.com.thalesdev.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thalesdev.gestao_vagas.exceptions.UserNotFoundException;
import br.com.thalesdev.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.thalesdev.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class ProfileCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public ProfileCompanyResponseDTO execute(UUID idCompany) {
        var company = this.companyRepository.findById(idCompany)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        var companyDto = ProfileCompanyResponseDTO.builder()
                .description(company.getDescription())
                .email(company.getEmail())
                .id(company.getId())
                .name(company.getName())
                .username(company.getUsername())
                .build();

        return companyDto;
    }

}
