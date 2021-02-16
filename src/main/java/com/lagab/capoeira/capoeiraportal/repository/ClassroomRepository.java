package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>, JpaSpecificationExecutor<Classroom> {
    Page<Classroom> findAllByOwnerOrderBySeasonDesc(Pageable pageable, Long ownerId);

    Page<Classroom> findAllByAcademyOrderBySeasonDesc(Pageable pageable, Long academyId);

    Optional<Classroom> getClassroomBySlug(String slug);

    Page<Classroom> findAllByAcademyAndSeason(Pageable pageable, Long academyId, Year season);

    @Query("select c.slug from Classroom c  where c.slug like %:slug%")
    List<String> getAllSlugs(String slug);
}
