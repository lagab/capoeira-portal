package com.lagab.capoeira.capoeiraportal.web.rest;

import com.lagab.capoeira.capoeiraportal.domain.Academy;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import com.lagab.capoeira.capoeiraportal.service.AcademyQueryService;
import com.lagab.capoeira.capoeiraportal.service.AcademyService;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyCriteria;
import com.lagab.capoeira.capoeiraportal.service.filter.VisibilityFilter;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/academy")
public class AcademyController {

    private final Logger log = LoggerFactory.getLogger(SchoolController.class);

    private final AcademyService academyService;

    private final AcademyQueryService academyQueryService;

    private static final String ENTITY_NAME = "school";

    public AcademyController(AcademyService academyService, AcademyQueryService academyQueryService) {
        this.academyService = academyService;
        this.academyQueryService = academyQueryService;
    }

    public VisibilityFilter getPublicVisibility() {
        VisibilityFilter visibilityFilter = new VisibilityFilter();
        visibilityFilter.setEquals(Visibility.PUBLIC);
        return visibilityFilter;
    }

    @GetMapping("/{id:.+}")
    public ResponseEntity<Academy> getAcademy(@PathVariable Long id) {
        Optional<Academy> academy = academyService.findById(id);
        if(academy.isPresent()){
            return new ResponseEntity<>(academy.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<Academy>> getAllAcademies(AcademyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get "+ENTITY_NAME+" by criteria: {}", criteria);
        if(criteria.getVisibility() == null ) {
            criteria.setVisibility(this.getPublicVisibility());
        }
        Page<Academy> lists = academyQueryService.findByCriteria(criteria,pageable);
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping("")
    public ResponseEntity<Academy> createAcademy(@RequestBody Academy academy) {
        log.debug("create "+ENTITY_NAME);
        if( academy.getId() != null ) {
            log.info(ENTITY_NAME+" should Be Null");
        }

        Academy result = academyService.create(academy);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Academy> updateAcademy(@RequestBody Academy school) {

        if( !academyService.exists(school.getId()) ) {
            return  ResponseEntity.notFound().build();
        }
        Academy result = academyService.update(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademy(@PathVariable Long id) {
        Optional<Academy> school = academyService.findById(id);
        if(school.isPresent()) {
            academyService.delete(school.get());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
