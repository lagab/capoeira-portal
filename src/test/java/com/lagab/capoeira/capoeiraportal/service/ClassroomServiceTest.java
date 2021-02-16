package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.CapoeiraPortalApplication;
import com.lagab.capoeira.capoeiraportal.repository.ClassroomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest(classes = CapoeiraPortalApplication.class)
public class ClassroomServiceTest {
    @InjectMocks
    private ClassroomService classroomService;
    @Mock
    private ClassroomRepository classroomRepository;


    @Test
    public void sugestClassRoomWithNoRecordsShouldReturnNextSlugWithId1() {
        Mockito.when(classroomRepository.getAllSlugs(Mockito.any(String.class))).thenReturn(Collections.emptyList());
        String suggest = classroomService.sugestsClassroom("test1");
        Assertions.assertEquals(suggest, "test1");
    }

    @Test
    public void sugestClassRoomShouldReturnNextSlugWithNoId() {
        Mockito.when(classroomRepository.getAllSlugs(Mockito.any(String.class))).thenReturn(new ArrayList<>(Arrays.asList("test1")));
        String suggest = classroomService.sugestsClassroom("test");
        Assertions.assertEquals(suggest, "test");
    }

    @Test
    public void sugestClassRoomShouldReturnNextSlugWith2() {

        Mockito.when(classroomRepository.getAllSlugs(Mockito.any(String.class))).thenReturn(new ArrayList<>(Arrays.asList("test", "test1")));
        String suggest = classroomService.sugestsClassroom("test1");

        Assertions.assertEquals(suggest, "test2");
    }

    @Test
    public void sugestClassRoomShouldReturnNextSlugWith12() {

        Mockito.when(classroomRepository.getAllSlugs(Mockito.any(String.class))).thenReturn(new ArrayList<>(Arrays.asList("test", "test1", "test11")));
        String suggest = classroomService.sugestsClassroom("test1");

        Assertions.assertEquals(suggest, "test12");
    }
}
