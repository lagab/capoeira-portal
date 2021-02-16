package com.lagab.capoeira.capoeiraportal.service.mapper;

import com.lagab.capoeira.capoeiraportal.domain.Classroom;
import com.lagab.capoeira.capoeiraportal.service.dto.ClassroomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Year;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    @Mapping(source = "season", target = "season", qualifiedByName = "yearToInt")
    ClassroomDto from(Classroom classroom);

    @Mapping(source = "season", target = "season", qualifiedByName = "intToYear")
    Classroom from(ClassroomDto classroomDto);

    @Named("intToYear")
    static Year intToYear(int year) {
        return Year.of(year);
    }

    @Named("yearToInt")
    static int yearToInt(Year year) {
        return year.getValue();
    }
}
