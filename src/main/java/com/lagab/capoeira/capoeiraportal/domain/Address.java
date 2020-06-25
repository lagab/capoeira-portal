package com.lagab.capoeira.capoeiraportal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String street;
    private String city;
    private String country;
    private Float latitude;
    private Float longitude;
}
