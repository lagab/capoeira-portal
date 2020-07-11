package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import com.lagab.capoeira.capoeiraportal.repository.AcademyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AcademyService {

    private final Logger log = LoggerFactory.getLogger(AcademyService.class);
    private final AcademyRepository academyRepository;

    public AcademyService(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return academyRepository.existsById(id);
    }

    public Academy create(Academy academy) {
        return academyRepository.save(academy);
    }

    public Academy update(Academy academy) {
        return academyRepository.save(academy);
    }

    public void delete(Academy academy) {
        academyRepository.delete(academy);
    }

    @Transactional(readOnly = true)
    public Optional<Academy> findById(Long id){
        return this.academyRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Academy> searchByName(Pageable pageable,String name){
        return academyRepository.findAllByNameContaining(pageable,name);
    }

    @Transactional(readOnly = true)
    public List<Academy> findAll() {
        return this.academyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Academy> findAllVisible(Pageable pageable) {
        return this.academyRepository.findAllByVisibility(pageable,Visibility.PUBLIC);
    }
}
