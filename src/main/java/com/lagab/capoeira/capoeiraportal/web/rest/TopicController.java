package com.lagab.capoeira.capoeiraportal.web.rest;


import com.lagab.capoeira.capoeiraportal.service.TopicService;
import com.lagab.capoeira.capoeiraportal.service.dto.TopicDto;
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
public class TopicController {

    private static final String HAS_AUTHORITY_CLASSROOM_WRITE = "hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_ADMIN')";
    private final Logger log = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;

    private static final String TOPIC_ENDPOINT = "/topic";
    private static final String ENTITY_NAME = "topic";


    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @DeleteMapping(TOPIC_ENDPOINT + "/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete " + ENTITY_NAME + " : {}", id);
        Optional<TopicDto> classroomDto = topicService.findById(id);
        if (classroomDto.isPresent()) {
            topicService.delete(classroomDto.get().getId());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(TOPIC_ENDPOINT + "/find/{id}")
    public ResponseEntity<TopicDto> get(@PathVariable Long id) {
        log.debug("REST request to get " + ENTITY_NAME + " : {}", id);
        Optional<TopicDto> level = topicService.findById(id);
        if (level.isPresent()) {
            return new ResponseEntity<>(level.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(TOPIC_ENDPOINT + "/{slug}")
    public ResponseEntity<TopicDto> getBySlug(@PathVariable String slug) {
        log.debug("REST request to get " + ENTITY_NAME + " : {}", slug);
        Optional<TopicDto> topic = topicService.findBySlug(slug);
        if (topic.isPresent()) {
            return new ResponseEntity<>(topic.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(TOPIC_ENDPOINT)
    public ResponseEntity<List<TopicDto>> getAll() {
        log.debug("REST request to get all " + ENTITY_NAME + " ");
        return ResponseEntity.ok().body(topicService.findAll());
    }

    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @PostMapping(TOPIC_ENDPOINT)
    public ResponseEntity<TopicDto> create(@Valid @RequestBody TopicDto topicDto) {
        log.debug("REST request to save " + ENTITY_NAME + " : {}", topicDto);
        if (topicDto.getId() != null) {
            throw new BadRequestAlertException("A new classrrom cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TopicDto result = topicService.create(topicDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PreAuthorize(HAS_AUTHORITY_CLASSROOM_WRITE)
    @PutMapping(TOPIC_ENDPOINT)
    public ResponseEntity<TopicDto> update(@Valid @RequestBody TopicDto topicDto) {
        log.debug("REST request to update " + ENTITY_NAME + " : {}", topicDto);
        if (!topicService.exists(topicDto.getId())) {
            return ResponseEntity.notFound().build();
        }
        TopicDto result = topicService.update(topicDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }


}
