package com.lagab.capoeira.capoeiraportal.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
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

    @Column(name = "logo_url")
    private String logoUrl;

    @NotNull
    @ManyToOne
    @JoinColumn(name="school_id", nullable=false)
    @JsonAlias({"school","school_id"})
    @JsonIgnoreProperties("address")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Visibility visibility;

    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonAlias({"address","address_id"})
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    private Address address;

}
