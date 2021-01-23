package com.lagab.capoeira.capoeiraportal.service.mapper;

import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
uses = { SchoolMapper.class })
public interface AcademyMapper {
    AcademyDto from(Academy academy);
    Academy from(AcademyDto academyDto);
}
