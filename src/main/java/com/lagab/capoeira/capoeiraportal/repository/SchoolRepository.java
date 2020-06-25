package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import com.lagab.capoeira.capoeiraportal.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "school")
public interface SchoolRepository extends CrudRepository<School, Long> {

    Page<School> findAllByStyle(Pageable pageable, Style style);
}
