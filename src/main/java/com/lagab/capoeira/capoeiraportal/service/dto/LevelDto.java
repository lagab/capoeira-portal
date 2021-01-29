package com.lagab.capoeira.capoeiraportal.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lagab.capoeira.capoeiraportal.domain.Level;
import com.lagab.capoeira.capoeiraportal.domain.enums.Phase;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = {"parent"})
public class LevelDto {
    private Long id;
    private String name;
    private String icon;
    private Phase phase;
    private boolean activated;
    @JsonIgnoreProperties({"parent","nextLevel"})
    private Level parent;
    private List<Level> nextLevel;
}
