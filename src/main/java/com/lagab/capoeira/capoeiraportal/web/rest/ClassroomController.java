package com.lagab.capoeira.capoeiraportal.web.rest;


import com.lagab.capoeira.capoeiraportal.service.ClassroomService;
import com.lagab.capoeira.capoeiraportal.service.dto.ClassroomDto;
import com.lagab.capoeira.capoeiraportal.service.dto.SlugSuggestion;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ClassroomController {

    private static final String HAS_AUTHORITY_CLASSROOM_WRITE = "hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_ADMIN')";
    private final Logger log = LoggerFactory.getLogger(ClassroomController.class);

    private final ClassroomService classroomService;

    private static final String CLASSROOM_ENDPOINT = "/classroom";
    private static final String ENTITY_NAME = "classroom";


    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @DeleteMapping(CLASSROOM_ENDPOINT + "/{id:\\d+}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        log.debug("REST request to delete " + ENTITY_NAME + " : {}", id);
        Optional<ClassroomDto> classroomDto = classroomService.findById(id);
        if (classroomDto.isPresent()) {
            classroomService.delete(classroomDto.get().getId());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(CLASSROOM_ENDPOINT + "/find/{id}")
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable Long id) {
        log.debug("REST request to get " + ENTITY_NAME + " : {}", id);
        Optional<ClassroomDto> level = classroomService.findById(id);
        if (level.isPresent()) {
            return new ResponseEntity<>(level.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(CLASSROOM_ENDPOINT + "/{slug}")
    public ResponseEntity<ClassroomDto> getClassroomBySlug(@PathVariable String slug) {
        log.debug("REST request to get " + ENTITY_NAME + " : {}", slug);
        Optional<ClassroomDto> level = classroomService.findBySlug(slug);
        if (level.isPresent()) {
            return new ResponseEntity<>(level.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(CLASSROOM_ENDPOINT + "/{slug}/suggests")
    public ResponseEntity<SlugSuggestion> suggetsClasroomSlugs(@PathVariable String slug) {
        return new ResponseEntity<>(classroomService.findSuggetsBySlug(slug), HttpStatus.OK);
    }

    @GetMapping(CLASSROOM_ENDPOINT)
    public ResponseEntity<List<ClassroomDto>> getAll() {
        log.debug("REST request to get all " + ENTITY_NAME + " ");
        return ResponseEntity.ok().body(classroomService.findAll());
    }

    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @PostMapping(CLASSROOM_ENDPOINT)
    public ResponseEntity<ClassroomDto> createClassRoom(@Valid @RequestBody ClassroomDto classroomDto) {
        log.debug("REST request to save " + ENTITY_NAME + " : {}", classroomDto);
        if (classroomDto.getId() != null) {
            throw new BadRequestAlertException("A new classrrom cannot already have an ID", ENTITY_NAME, "idexists");
        }

        ClassroomDto result = classroomService.create(classroomDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @PutMapping(CLASSROOM_ENDPOINT)
    public ResponseEntity<ClassroomDto> updateClassroom(@Valid @RequestBody ClassroomDto classroomDto) {
        log.debug("REST request to update " + ENTITY_NAME + " : {}", classroomDto);
        if (!classroomService.exists(classroomDto.getId())) {
            return ResponseEntity.notFound().build();
        }
        ClassroomDto result = classroomService.update(classroomDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }


}
