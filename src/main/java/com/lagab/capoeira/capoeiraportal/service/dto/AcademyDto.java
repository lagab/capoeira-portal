package com.lagab.capoeira.capoeiraportal.service.dto;

import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import lombok.Data;



@Data
public class AcademyDto {

    private Long id;
    private String name;
    private String logoUrl;
    private SchoolDto school;
    private Visibility visibility;
    //Address
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
