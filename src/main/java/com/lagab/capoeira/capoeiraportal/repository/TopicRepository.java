package com.lagab.capoeira.capoeiraportal.repository;

import com.lagab.capoeira.capoeiraportal.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    Page<Topic> findAllByClassroom(Pageable pageable, Long classroom);

    Optional<Topic> getTopicBySlug(String slug);
}
