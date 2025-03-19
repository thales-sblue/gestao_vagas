package br.com.thalesdev.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thalesdev.gestao_vagas.exceptions.JobNotFoundException;
import br.com.thalesdev.gestao_vagas.exceptions.UserNotFoundException;
import br.com.thalesdev.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.thalesdev.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.thalesdev.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public void execute(UUID candidateId, UUID jobId) {

        this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

    }

}
