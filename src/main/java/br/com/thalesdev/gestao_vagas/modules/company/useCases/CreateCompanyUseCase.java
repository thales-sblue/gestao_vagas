package br.com.thalesdev.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thalesdev.gestao_vagas.exceptions.UserFoundException;
import br.com.thalesdev.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.thalesdev.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public CompanyEntity execute(CompanyEntity companyEntity) {

        this.companyRepository
            .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
            .ifPresent(company -> {
                throw new UserFoundException("Company username or email already exists");
            });

            var password = passwordEncoder.encode(companyEntity.getPassword());
            companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);        
    }
}
