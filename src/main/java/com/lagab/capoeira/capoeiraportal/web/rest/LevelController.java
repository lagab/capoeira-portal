package com.lagab.capoeira.capoeiraportal.web.rest;


import com.lagab.capoeira.capoeiraportal.service.LevelService;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LevelController {

    private final Logger log = LoggerFactory.getLogger(SchoolController.class);

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
    public ResponseEntity<List<LevelDto>> getAllAcademies() {
        log.debug("REST request to get all " + ENTITY_NAME + " ");
        List<LevelDto> lists = levelService.findAll();
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping(LEVEL_ENDPOINT)
    public ResponseEntity<LevelDto> createAcademy(@RequestBody LevelDto levelDto) {
        log.debug("REST request to save " + ENTITY_NAME + " : {}", levelDto);
        if (levelDto.getId() != null) {
            throw new BadRequestAlertException("A new contactFolder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        LevelDto result = levelService.create(levelDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping(LEVEL_ENDPOINT + "/{id}")
    public ResponseEntity<LevelDto> updateAcademy(@RequestBody LevelDto levelDto) {
        log.debug("REST request to update " + ENTITY_NAME + " : {}", levelDto);
        if (!levelService.exists(levelDto.getId())) {
            return ResponseEntity.notFound().build();
        }
        LevelDto result = levelService.update(levelDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping(LEVEL_ENDPOINT + "/{id}")
    public ResponseEntity<Void> deleteAcademy(@PathVariable Long id) {
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
