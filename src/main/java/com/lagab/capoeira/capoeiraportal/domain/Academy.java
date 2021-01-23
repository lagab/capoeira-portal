package com.lagab.capoeira.capoeiraportal.domain;

import com.fasterxml.jackson.annotation.*;
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
public class Academy extends AbstractAuditingEntity {

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
    @JsonProperty("school")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Visibility visibility;

    //Address
    @Column(name="address_1")
    private String addressLine1;
    @Column(name="address_2")
    private String addressLine2;
    @Column(name="postal_code")
    private String postalCode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;

}
