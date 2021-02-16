package com.lagab.capoeira.capoeiraportal.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotNull
    @Column(name = "slug", unique = true)
    private String slug;
    private String description;
    @Column(name = "season",
            columnDefinition = "smallint")
    @Convert(converter = YearAttributeConverter.class)
    private Year season;
    private String theme;
    @Column(name = "owner_id")
    private Long owner;
    @Column(name = "academy_id")
    @JoinColumn(name = "academy_id", nullable = false)
    private Long academy;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    @Column(name = "activation_code")
    private String activationCode;

    @ManyToMany
    @JoinTable(
            name = "classroom_teacher",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    @BatchSize(size = 1000)
    private Set<User> teachers = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "classroom_student",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "classroom_id", referencedColumnName = "id")})
    @BatchSize(size = 1000)
    private Set<User> students = new HashSet<>();
}
