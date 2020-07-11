package com.lagab.capoeira.capoeiraportal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name="address_1")
    private String addressLine1;
    @Column(name="address_2")
    private String addressLine2;
    @Column(name="postal_code")
    private String postcode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
