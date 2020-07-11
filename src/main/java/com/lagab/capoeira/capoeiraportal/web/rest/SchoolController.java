package com.lagab.capoeira.capoeiraportal.web.rest;

import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.security.Authorities;
import com.lagab.capoeira.capoeiraportal.service.SchoolService;
import com.lagab.capoeira.capoeiraportal.web.rest.util.HeaderUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
        Optional<School> school = schoolService.findById(id);
        if(school.isPresent()){
            return new ResponseEntity<>(school.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<School>> getAllSchool() {
        List<School> lists = schoolService.findAll();
        return ResponseEntity.ok().body(lists);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        log.info("create School");
        if( school.getId() != null ) {

        }

        School result = schoolService.create(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<School> updateSchool(@RequestBody School school) {

        if( !schoolService.exists(school.getId()) ) {
            return  ResponseEntity.notFound().build();
        }
        School result = schoolService.update(school);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + Authorities.ADMIN + "\")")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        Optional<School> school = schoolService.findById(id);
        if(school.isPresent()) {
            schoolService.delete(school.get());
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
