package com.lagab.capoeira.capoeiraportal.service;


import com.lagab.capoeira.capoeiraportal.domain.Level;
import com.lagab.capoeira.capoeiraportal.domain.enums.Phase;
import com.lagab.capoeira.capoeiraportal.errors.InfiniteLoopException;
import com.lagab.capoeira.capoeiraportal.repository.LevelRepository;
import com.lagab.capoeira.capoeiraportal.service.dto.LevelDto;
import com.lagab.capoeira.capoeiraportal.service.mapper.LevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LevelService {

    private final Logger log = LoggerFactory.getLogger(LevelService.class);
    private final LevelRepository levelRepository;
    @Inject
    private LevelMapper levelMapper;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return levelRepository.existsById(id);
    }

    public LevelDto create(LevelDto levelDto) {
        return save(levelDto);
    }

    public LevelDto update(LevelDto levelDto) {
        return save(levelDto);
    }

    private LevelDto save(LevelDto levelDto) {
        validateLevel(levelDto);
        Level result = levelRepository.save(levelMapper.from(levelDto));
        return levelMapper.from(result);
    }

    private void validateLevel(LevelDto levelDto) {
        if (levelDto.getId() != null) {
            Level level = levelRepository.getOne(levelDto.getId());
            if (level.getAllLevels().contains(levelMapper.from(levelDto.getParent()))) {
                throw new InfiniteLoopException("Level", "This level can't be parent of his own child");
            }
        }
    }

    public void delete(Long id) {
        levelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<LevelDto> findById(Long id) {
        return Optional.ofNullable(levelMapper.from(levelRepository.findById(id).orElse(null)));
    }

    @Transactional(readOnly = true)
    public Page<LevelDto> searchByPhase(Pageable pageable, Phase phase) {
        return levelRepository.findAllByPhase(pageable, phase).map(levelMapper::from);
    }

    public Page<LevelDto> searchBySchoolId(Pageable pageable, Long schoolId) {
        return levelRepository.findAllBySchoolId(pageable, schoolId).map(levelMapper::from);
    }


    @Transactional(readOnly = true)
    public List<LevelDto> findAll() {
        List<Level> results = levelRepository.findAll();
        return results.stream().map(levelMapper::from).collect(Collectors.toList());
    }


}
