package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long>, JpaSpecificationExecutor<Academy> {

    Page<Academy> findAllByNameContaining(Pageable pageable,String name);
    Page<Academy> findAllBySchool(Pageable pageable, School school);
    Page<Academy> findAllByVisibility(Pageable pageable, Visibility visibility);

}
