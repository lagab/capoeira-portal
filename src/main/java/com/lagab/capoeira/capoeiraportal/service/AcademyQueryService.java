package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.domain.*;
import com.lagab.capoeira.capoeiraportal.repository.AcademyRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyCriteria;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.AcademyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class AcademyQueryService extends QueryService<Academy>{

    private final Logger log = LoggerFactory.getLogger(AcademyQueryService.class);

    private final AcademyRepository academyRepository;
    @Inject
    private AcademyMapper academyMapper;

    public AcademyQueryService(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    /**
     * Return a {@link List} of {@link Academy} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Academy> findByCriteria(AcademyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Academy> specification = createSpecification(criteria);
        return academyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Academy} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcademyDto> findByCriteria(AcademyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Academy> specification = createSpecification(criteria);
        return academyRepository.findAll(specification, page).map(academyMapper::from);
    }

    /**
     * Function to convert ContactCriteria to a {@link Specification}
     */
    private Specification<Academy> createSpecification(AcademyCriteria criteria) {
        Specification<Academy> specification = Specification.where(null);
        if (criteria != null) {
            /** todo
             * To finish
             */
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Academy_.id));
            }
            if (criteria.getVisibility() != null) {
                specification = specification.and(buildSpecification(criteria.getVisibility(), Academy_.visibility));
            }
            if (criteria.getSchoolId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSchoolId(), Academy_.school,School_.id));
            }
        }
        return specification;
    }
}
