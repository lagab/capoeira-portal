package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.CapoeiraPortalApplication;
import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.domain.User;
import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import com.lagab.capoeira.capoeiraportal.repository.SchoolRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.SchoolDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CapoeiraPortalApplication.class)
@Transactional
public class SchoolServiceTest {

    @Autowired
    private SchoolService schoolService;


    public School initSchool() {
        School school = new School();
        school.setName("Capoeira School Name");
        school.setFounder("Mestre Bimba");
        school.setLogoUrl("http://placehold.it/50x50");
        return school;
    }

    @Test
    @Transactional
    public void assertThatSchoolShouldNotCreatedWithoutName() {
        SchoolDto schoolToCreate = new SchoolDto();
        schoolToCreate.setFounder("Mestre Bimba");
        schoolToCreate.setLogoUrl("http://placehold.it/50x50");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            schoolService.create(schoolToCreate);
        });

    }
    public void assertThatSchoolCreatedWithStyle() {
        School school = initSchool();
        school.setStyle(Style.REGIONAL);

    }
}
