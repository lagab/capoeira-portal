package com.lagab.capoeira.capoeiraportal.web.rest;


import com.lagab.capoeira.capoeiraportal.security.Authorities;
import com.lagab.capoeira.capoeiraportal.service.LevelService;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LevelController {

    private final Logger log = LoggerFactory.getLogger(LevelController.class);
    private static final String HAS_AUTHORITY_LEVEL_WRITE = "hasAuthority('" + Authorities.MASTER + "') or hasAuthority('" + Authorities.ADMIN + "')";

    private final LevelService levelService;

    private static final String LEVEL_ENDPOINT = "/level";
    private static final String ENTITY_NAME = "level";


    @GetMapping(LEVEL_ENDPOINT + "/{id:.+}")
    public ResponseEntity<LevelDto> getLevel(@PathVariable Long id) {
        log.debug("REST request to get " + ENTITY_NAME + " : {}", id);
        Optional<LevelDto> level = levelService.findById(id);
        if (level.isPresent()) {
            return new ResponseEntity<>(level.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(LEVEL_ENDPOINT)
    public ResponseEntity<List<LevelDto>> getAllLevels() {
        log.debug("REST request to get all " + ENTITY_NAME + " ");
        List<LevelDto> lists = levelService.findAll();
        return ResponseEntity.ok().body(lists);
    }

    @PreAuthorize(HAS_AUTHORITY_LEVEL_WRITE)
    @PostMapping(LEVEL_ENDPOINT)
    public ResponseEntity<LevelDto> createLevel(@RequestBody LevelDto levelDto) {
        log.debug("REST request to save " + ENTITY_NAME + " : {}", levelDto);
        if (levelDto.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }

        LevelDto result = levelService.create(levelDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PreAuthorize(HAS_AUTHORITY_LEVEL_WRITE)
    @PutMapping(LEVEL_ENDPOINT + "/{id}")
    public ResponseEntity<LevelDto> updateLevel(@RequestBody LevelDto levelDto) {
        log.debug("REST request to update " + ENTITY_NAME + " : {}", levelDto);
        if (!levelService.exists(levelDto.getId())) {
            return ResponseEntity.notFound().build();
        }
        LevelDto result = levelService.update(levelDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PreAuthorize(HAS_AUTHORITY_LEVEL_WRITE)
    @DeleteMapping(LEVEL_ENDPOINT + "/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete " + ENTITY_NAME + " : {}", id);
        Optional<LevelDto> academyDto = levelService.findById(id);
        if (academyDto.isPresent()) {
            levelService.delete(academyDto.get().getId());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
