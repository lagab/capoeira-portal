package com.lagab.capoeira.capoeiraportal.web.rest;

import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.security.Authorities;
import com.lagab.capoeira.capoeiraportal.service.SchoolService;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/school")
public class SchoolController {

    private final Logger log = LoggerFactory.getLogger(SchoolController.class);

    private final SchoolService schoolService;

    private static final String ENTITY_NAME = "school";

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/{id:.+}")
    public ResponseEntity<School> getSchool(@PathVariable Long id) {
        log.debug("REST request to get "+ENTITY_NAME+" : {}", id);
        Optional<School> school = schoolService.findById(id);
        if(school.isPresent()){
            return new ResponseEntity<>(school.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<School>> getAllSchool() {
        log.debug("REST request to get all "+ENTITY_NAME+" ");
        List<School> lists = schoolService.findAll();
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        log.debug("REST request to save "+ENTITY_NAME+" : {}", school);
        if( school.getId() != null ) {
            throw new BadRequestAlertException("A new contactFolder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        School result = schoolService.create(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<School> updateSchool(@RequestBody School school) {
        log.debug("REST request to update "+ENTITY_NAME+" : {}", school);
        if( !schoolService.exists(school.getId()) ) {
            return  ResponseEntity.notFound().build();
        }
        School result = schoolService.update(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete "+ENTITY_NAME+" : {}", id);
        Optional<School> school = schoolService.findById(id);
        if(school.isPresent()) {
            schoolService.delete(school.get());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
