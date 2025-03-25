package br.com.thalesdev.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.thalesdev.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.thalesdev.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.thalesdev.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.thalesdev.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.thalesdev.gestao_vagas.utils.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

        private MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Autowired
        private CompanyRepository companyRepository;

        @Before
        public void setUp() {
                mvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @Test
        public void should_be_able_to_create_a_new_job() throws Exception {

                var company = CompanyEntity.builder()
                                .description("DESCRIPTION_TEST")
                                .email("email@company.com")
                                .name("NAME_TEST")
                                .password("PASSWORD_TEST")
                                .username("USERNAMETEST")
                                .build();

                companyRepository.saveAndFlush(company);

                var createdJobDto = CreateJobDTO.builder()
                                .description("DESCRIPTION_TEST")
                                .benefits("BENEFITS_TEST")
                                .level("LEVEL_TEST")
                                .build();

                var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createdJobDto))
                                .header("Authorization",
                                                TestUtils.generateToken(
                                                                company.getId(),
                                                                "JAVAGAS@123#")))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                System.out.println(result);
        }

        @Test
        public void should_not_be_able_to_create_a_new_job_with_company_not_found() throws Exception {
                var createdJobDto = CreateJobDTO.builder()
                                .description("DESCRIPTION_TEST")
                                .benefits("BENEFITS_TEST")
                                .level("LEVEL_TEST")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createdJobDto))
                                .header("Authorization",
                                                TestUtils.generateToken(
                                                                UUID.randomUUID(),
                                                                "JAVAGAS@123#")))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        }

}
