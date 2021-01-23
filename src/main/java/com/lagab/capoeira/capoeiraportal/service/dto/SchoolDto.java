package com.lagab.capoeira.capoeiraportal.service.dto;

import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import lombok.Data;

import java.io.Serializable;

@Data
public class SchoolDto implements Serializable {
    private Long id;
    private String name;
    private String founder;
    private String logoUrl;
    private Style style;
    //Address
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
