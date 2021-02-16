package com.lagab.capoeira.capoeiraportal.service.dto;

import com.lagab.capoeira.capoeiraportal.config.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TopicDto {

    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @Pattern(regexp = Constants.SLUG_REGEX)
    private String slug;
    private Long classroom;
}
