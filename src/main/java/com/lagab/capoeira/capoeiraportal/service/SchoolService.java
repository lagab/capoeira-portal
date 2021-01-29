package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.repository.SchoolRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.SchoolDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.SchoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolService.class);
    private final SchoolRepository schoolRepository;
    @Inject
    private SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return schoolRepository.existsById(id);
    }

    public SchoolDto create(SchoolDto school) {
        return save(school);
    }

    public SchoolDto update(SchoolDto school) {
        return save(school);
    }

    private SchoolDto save(SchoolDto schoolDto){
        School result = schoolRepository.save(schoolMapper.from(schoolDto));
        return schoolMapper.from(result);
    }

    public void delete(Long id) {
        schoolRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<SchoolDto> findById(Long id){
        return Optional.ofNullable(schoolMapper.from(schoolRepository.findById(id).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Optional<SchoolDto> searchByName(String name){
        return Optional.ofNullable(schoolMapper.from(schoolRepository.findByName(name).orElse(null)));
    }


    @Transactional(readOnly = true)
    public List<SchoolDto> findAll() {
        List<School> results = schoolRepository.findAll();
        return results.stream().map(schoolMapper::from).collect(Collectors.toList());
    }


}
