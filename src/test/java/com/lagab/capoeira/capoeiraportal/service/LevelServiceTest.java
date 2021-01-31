package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.CapoeiraPortalApplication;
import com.lagab.capoeira.capoeiraportal.domain.enums.Phase;
import com.lagab.capoeira.capoeiraportal.errors.InfiniteLoopException;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import com.lagab.capoeira.capoeiraportal.service.dto.SchoolDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.LevelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = CapoeiraPortalApplication.class)
public class LevelServiceTest {
    @Autowired
    private LevelService levelService;
    @Inject
    private SchoolService schoolService;
    @Autowired
    private LevelMapper levelMapper;

    private SchoolDto school;

    private LevelDto createLevel(String name) {

        LevelDto levelDto = new LevelDto();
        levelDto.setName(name);
        levelDto.setPhase(Phase.BABY);
        levelDto.setSchool(school);
        return levelDto;
    }

    private LevelDto createLevel(String name, LevelDto parent) {
        LevelDto levelDto = createLevel(name);
        levelDto.setParent(parent);
        return levelDto;
    }

    @BeforeTestExecution
    public void setUp() {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("SCHOOL_NAME");
        school = schoolService.create(schoolDto);
    }

    @Test
    public void createLevelShouldBeOk() {
        LevelDto levelCreated = levelService.create(createLevel("Level test"));
        assertThat(levelCreated.getName().equals("Level test"));
    }

    @Test
    public void createLevelWithInfiniteparentShouldThrowInfiniteLoopException() {
        LevelDto level1 = levelService.create(createLevel("Level 1"));
        LevelDto level2 = levelService.create(createLevel("Level 2", level1));
        assertThatThrownBy(() -> {
                    level1.setParent(level2);
                    levelService.update(level1);
                }
        ).isInstanceOf(InfiniteLoopException.class);
    }
}
