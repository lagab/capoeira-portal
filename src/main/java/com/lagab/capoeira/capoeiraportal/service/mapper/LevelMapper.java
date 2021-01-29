package com.lagab.capoeira.capoeiraportal.service.mapper;

import com.lagab.capoeira.capoeiraportal.domain.Level;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    LevelDto from(Level level);
    Level from(LevelDto levelDto);
}
