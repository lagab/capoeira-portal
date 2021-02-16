package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.Classroom;
import com.lagab.capoeira.capoeiraportal.repository.ClassroomRepository;
import com.lagab.capoeira.capoeiraportal.security.util.RandomUtil;
import com.lagab.capoeira.capoeiraportal.service.dto.ClassroomDto;
import com.lagab.capoeira.capoeiraportal.service.dto.SlugSuggestion;
import com.lagab.capoeira.capoeiraportal.service.mapper.ClassroomMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomService {

    private final Logger log = LoggerFactory.getLogger(ClassroomService.class);
    private final ClassroomRepository classroomRepository;

    @Inject
    private ClassroomMapper classroomMapper;

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return classroomRepository.existsById(id);
    }

    public ClassroomDto create(ClassroomDto classroomDto) {
        if (StringUtils.isEmpty(classroomDto.getActivationCode())) {
            classroomDto.setActivationCode(RandomUtil.generateKey(10));
        }
        return save(classroomDto);
    }

    public ClassroomDto update(ClassroomDto classroomDto) {
        return save(classroomDto);
    }

    private ClassroomDto save(ClassroomDto classroomDto) {
        Classroom result = classroomRepository.save(classroomMapper.from(classroomDto));
        return classroomMapper.from(result);
    }


    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ClassroomDto> findById(Long id) {
        return Optional.ofNullable(classroomMapper.from(classroomRepository.findById(id).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Optional<ClassroomDto> findBySlug(String slug) {
        return Optional.ofNullable(classroomMapper.from(classroomRepository.getClassroomBySlug(slug).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Page<ClassroomDto> searchBySeason(Pageable pageable, Long academyId, Year season) {
        return classroomRepository.findAllByAcademyAndSeason(pageable, academyId, season).map(classroomMapper::from);
    }

    public Page<ClassroomDto> searchByAcademy(Pageable pageable, Long academyId) {
        return classroomRepository.findAllByAcademyOrderBySeasonDesc(pageable, academyId).map(classroomMapper::from);
    }


    @Transactional(readOnly = true)
    public List<ClassroomDto> findAll() {
        List<Classroom> results = classroomRepository.findAll();
        return results.stream().map(classroomMapper::from).collect(Collectors.toList());
    }


    public SlugSuggestion findSuggetsBySlug(String slug) {
        SlugSuggestion slugSuggestion = new SlugSuggestion();
        Optional<Classroom> classroom = classroomRepository.getClassroomBySlug(slug);
        if (!classroom.isPresent()) {
            slugSuggestion.setExists(false);
        } else {
            slugSuggestion.setExists(true);
            slugSuggestion.getSuggests().add(sugestsClassroom(slug));
        }
        return slugSuggestion;
    }

    public String sugestsClassroom(String slug) {
        Pattern p = Pattern.compile("\\d+$");
        Matcher slugMatcher = p.matcher(slug);
        String start = slug;
        if (slugMatcher.find()) {
            start = slug.substring(0, slug.indexOf(slugMatcher.group(0)));
        }
        List<String> similars = classroomRepository.getAllSlugs(start);
        if (similars.isEmpty() || !similars.contains(slug)) {
            return slug;
        }
        int lastId = 0;
        List<Integer> slugs = similars.stream().map(s -> {
            Matcher m = p.matcher(s);
            if (m.find()) {
                return Integer.valueOf(m.group(0));
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(slugs)) {
            lastId = Collections.max(slugs);
        }
        lastId++;
        return sugestsClassroom(start + lastId);
    }

    // Todo: Test if slug match a slug regex
}
