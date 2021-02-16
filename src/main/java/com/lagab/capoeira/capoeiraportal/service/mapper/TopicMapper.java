package com.lagab.capoeira.capoeiraportal.service.mapper;

import com.lagab.capoeira.capoeiraportal.domain.Topic;
import com.lagab.capoeira.capoeiraportal.service.dto.TopicDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDto from(Topic topic);

    Topic from(TopicDto topicDto);
}
