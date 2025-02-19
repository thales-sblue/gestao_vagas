package br.com.thalesdev.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thalesdev.gestao_vagas.exceptions.UserFoundException;
import br.com.thalesdev.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.thalesdev.gestao_vagas.modules.candidate.controllers.CandidateEntity;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;    

    public CandidateEntity execute(CandidateEntity candidateEntity){       

        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent(candidate -> {
            throw new UserFoundException("Username or email already exists");
        });

        return this.candidateRepository.save(candidateEntity);
    }
    
}
