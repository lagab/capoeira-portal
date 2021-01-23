package com.lagab.capoeira.capoeiraportal.service.mapper;

import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.service.dto.SchoolDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolDto from(School school);
    School from(SchoolDto schoolDto);
}
