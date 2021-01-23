package com.lagab.capoeira.capoeiraportal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class LocalizableEntity {

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
