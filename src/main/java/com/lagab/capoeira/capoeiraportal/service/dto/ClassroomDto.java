package com.lagab.capoeira.capoeiraportal.service.dto;

import com.lagab.capoeira.capoeiraportal.config.Constants;
import com.lagab.capoeira.capoeiraportal.domain.User;
import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class ClassroomDto {

    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @Pattern(regexp = Constants.SLUG_REGEX)
    private String slug;
    private String description;
    private Integer season;
    private String theme;
    @NotNull
    private Long owner;
    @NotNull
    private Long academy;
    @NotNull
    private Visibility visibility;
    private String activationCode;

    private Set<User> teachers;
    private Set<User> students;
}
