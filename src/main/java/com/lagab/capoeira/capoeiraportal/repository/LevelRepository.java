package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.Level;
import com.lagab.capoeira.capoeiraportal.domain.enums.Phase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long>, JpaSpecificationExecutor<Level> {
    Page<Level> findAllByPhase(Pageable pageable, Phase phase);

    List<Level> findAllByParentIsNullAndPhase(Phase phase);

    List<Level> findAllByParentIsNull();

    List<Level> findAllBySchoolId(Long schoolId);

    Page<Level> findAllBySchoolId(Pageable pageable, Long schoolId);

    List<Level> findAllBySchoolIdAndParent(Long schoolId, Level parent);
}
