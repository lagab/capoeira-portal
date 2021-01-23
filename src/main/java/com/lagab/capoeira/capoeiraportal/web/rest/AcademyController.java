package com.lagab.capoeira.capoeiraportal.web.rest;

import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import com.lagab.capoeira.capoeiraportal.service.dto.AcademyDto;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.security.SecurityUtils;
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
@RequestMapping("/academy")
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
    public ResponseEntity<AcademyDto> getAcademy(@PathVariable Long id) {
        log.debug("REST request to get "+ENTITY_NAME+" : {}", id);
        Optional<AcademyDto> academy = academyService.findById(id);
        if(academy.isPresent()){
            return new ResponseEntity<>(academy.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<AcademyDto>> getAllAcademies(AcademyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get "+ENTITY_NAME+" by criteria: {}", criteria);
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        log.info(userLogin.get());
        if(criteria.getVisibility() == null ) {
            criteria.setVisibility(this.getPublicVisibility());
        }
        Page<AcademyDto> lists = academyQueryService.findByCriteria(criteria,pageable);
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping("")
    public ResponseEntity<AcademyDto> createAcademy(@RequestBody AcademyDto academy) {
        log.debug("REST request to save "+ENTITY_NAME+" : {}", academy);
        if( academy.getId() != null ) {
            throw new BadRequestAlertException("A new contactFolder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        AcademyDto result = academyService.create(academy);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademyDto> updateAcademy(@RequestBody AcademyDto academy) {
        log.debug("REST request to update "+ENTITY_NAME+" : {}", academy);
        if( !academyService.exists(academy.getId()) ) {
            return  ResponseEntity.notFound().build();
        }
        AcademyDto result = academyService.update(academy);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademy(@PathVariable Long id) {
        log.debug("REST request to delete "+ENTITY_NAME+" : {}", id);
        Optional<AcademyDto> academyDto = academyService.findById(id);
        if(academyDto.isPresent()) {
            academyService.delete(academyDto.get().getId());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
