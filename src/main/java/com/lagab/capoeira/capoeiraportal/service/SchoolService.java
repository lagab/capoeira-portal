package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolService.class);
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return schoolRepository.existsById(id);
    }

    public School create(School school) {
        return schoolRepository.save(school);
    }

    public School update(School school) {
        return schoolRepository.save(school);
    }

    public void delete(School school) {
        schoolRepository.delete(school);
    }

    @Transactional(readOnly = true)
    public Optional<School> findById(Long id){
        return this.schoolRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<School> searchByName(String name){
        return schoolRepository.findByName(name);
    }


    @Transactional(readOnly = true)
    public List<School> findAll() {
        return this.schoolRepository.findAll();
    }


}
