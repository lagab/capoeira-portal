package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import com.lagab.capoeira.capoeiraportal.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {

    Page<School> findAllByStyle(Pageable pageable, Style style);
    Optional<School> findByName(String name);
}
