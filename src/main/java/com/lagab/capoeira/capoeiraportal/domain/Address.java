package com.lagab.capoeira.capoeiraportal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public abstract class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String addressLine1;
    private String addressLine2;
    private String postcode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
