package br.com.thalesdev.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thalesdev.gestao_vagas.exceptions.UserFoundException;
import br.com.thalesdev.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.thalesdev.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;
    
    public CompanyEntity execute(CompanyEntity companyEntity) {

        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
            .ifPresent(company -> {
                throw new UserFoundException("Company username or email already exists");
            });

        return this.companyRepository.save(companyEntity);        
    }
}
