package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import com.lagab.capoeira.capoeiraportal.repository.AcademyRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.AcademyMapper;
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
public class AcademyService {

    private final Logger log = LoggerFactory.getLogger(AcademyService.class);
    private final AcademyRepository academyRepository;
    @Inject
    private AcademyMapper academyMapper;

    public AcademyService(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return academyRepository.existsById(id);
    }

    private AcademyDto save(AcademyDto academyDto){
        Academy result = academyRepository.save(academyMapper.from(academyDto));
        return academyMapper.from(result);
    }

    public AcademyDto create(AcademyDto academy) {
        return save(academy);
    }

    public AcademyDto update(AcademyDto academy) {
        return save(academy);
    }

    public void delete(Long id) {
        academyRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<AcademyDto> findById(Long id){
        return Optional.ofNullable(academyMapper.from(academyRepository.findById(id).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Page<AcademyDto> searchByName(Pageable pageable,String name){
        return academyRepository.findAllByNameContaining(pageable,name).map(academyMapper::from);
    }

    @Transactional(readOnly = true)
    public List<AcademyDto> findAll() {
        List<Academy> results = this.academyRepository.findAll();
        return results.stream().map(academyMapper::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AcademyDto> findAllVisible(Pageable pageable) {
        return this.academyRepository.findAllByVisibility(pageable,Visibility.PUBLIC).map(academyMapper::from);
    }
}
