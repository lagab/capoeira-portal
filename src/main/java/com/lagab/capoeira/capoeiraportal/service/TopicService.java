package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.Topic;
import com.lagab.capoeira.capoeiraportal.repository.TopicRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.TopicDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.TopicMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicService.class);
    private final TopicRepository topicRepository;

    @Inject
    private TopicMapper topicMapper;

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return topicRepository.existsById(id);
    }

    public TopicDto create(TopicDto topicDto) {
        return save(topicDto);
    }

    public TopicDto update(TopicDto topicDto) {
        return save(topicDto);
    }

    private TopicDto save(TopicDto topicDto) {
        Topic result = topicRepository.save(topicMapper.from(topicDto));
        return topicMapper.from(result);
    }


    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<TopicDto> findById(Long id) {
        return Optional.ofNullable(topicMapper.from(topicRepository.findById(id).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Optional<TopicDto> findBySlug(String slug) {
        return Optional.ofNullable(topicMapper.from(topicRepository.getTopicBySlug(slug).orElse(null)));
    }

    public Page<TopicDto> findByClassroom(Pageable pageable, Long classroom) {
        return topicRepository.findAllByClassroom(pageable, classroom).map(topicMapper::from);
    }


    @Transactional(readOnly = true)
    public List<TopicDto> findAll() {
        List<Topic> results = topicRepository.findAll();
        return results.stream().map(topicMapper::from).collect(Collectors.toList());
    }
}
