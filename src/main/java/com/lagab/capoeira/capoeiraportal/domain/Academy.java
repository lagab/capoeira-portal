package com.lagab.capoeira.capoeiraportal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Academy extends AbstractAuditingEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="school_id", nullable=false)
    private School school;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String country;

    private Float latitude;

    private Float longitude;

}
