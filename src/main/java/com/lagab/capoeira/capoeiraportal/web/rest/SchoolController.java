package com.lagab.capoeira.capoeiraportal.web.rest;

import com.lagab.capoeira.capoeiraportal.security.Authorities;
import com.lagab.capoeira.capoeiraportal.service.LevelService;
import com.lagab.capoeira.capoeiraportal.service.SchoolService;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import com.lagab.capoeira.capoeiraportal.service.dto.SchoolDto;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SchoolController {

    private static final String HAS_AUTHORITY_SCHOOL_WRITE = "hasAuthority('" + Authorities.MASTER + "') or hasAuthority('" + Authorities.ADMIN + "')";
    private final Logger log = LoggerFactory.getLogger(SchoolController.class);

    private final SchoolService schoolService;
    private final LevelService levelService;
    private static final String SCHOOL_ENDPOINT = "/school";
    private static final String SCHOOL_LEVEL_ENDPOINT = "/school/{shoolId}/level";
    private static final String ENTITY_NAME = "school";


    @GetMapping(SCHOOL_ENDPOINT + "/{id:.+}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable Long id) {
        log.debug("REST request to get "+ENTITY_NAME+" : {}", id);
        Optional<SchoolDto> school = schoolService.findById(id);
        if(school.isPresent()){
            return new ResponseEntity<>(school.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(SCHOOL_ENDPOINT)
    public ResponseEntity<List<SchoolDto>> getAllSchool() {
        log.debug("REST request to get all "+ENTITY_NAME+" ");
        List<SchoolDto> lists = schoolService.findAll();
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping(SCHOOL_ENDPOINT)
    @PreAuthorize(HAS_AUTHORITY_SCHOOL_WRITE)
    public ResponseEntity<SchoolDto> createSchool(@RequestBody SchoolDto school) {
        log.debug("REST request to save "+ENTITY_NAME+" : {}", school);
        if( school.getId() != null ) {
            throw new BadRequestAlertException("A new contactFolder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        SchoolDto result = schoolService.create(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping(SCHOOL_ENDPOINT + "/{id}")
    @PreAuthorize(HAS_AUTHORITY_SCHOOL_WRITE)
    public ResponseEntity<SchoolDto> updateSchool(@RequestBody SchoolDto school) {
        log.debug("REST request to update "+ENTITY_NAME+" : {}", school);
        if( !schoolService.exists(school.getId()) ) {
            return  ResponseEntity.notFound().build();
        }
        SchoolDto result = schoolService.update(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping(SCHOOL_ENDPOINT + "/{id}")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete " + ENTITY_NAME + " : {}", id);
        Optional<SchoolDto> school = schoolService.findById(id);
        if (school.isPresent()) {
            schoolService.delete(school.get().getId());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(SCHOOL_LEVEL_ENDPOINT)
    public ResponseEntity<Page<LevelDto>> findSchoolLevels(@PathVariable Long shoolId, Pageable pageable) {
        Page<LevelDto> lists = levelService.searchBySchoolId(pageable, shoolId);
        return ResponseEntity.ok().body(lists);
    }

}
