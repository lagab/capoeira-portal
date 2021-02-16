package com.lagab.capoeira.capoeiraportal.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SlugSuggestion {

    private boolean exists;
    private List<String> suggests = new ArrayList<>();
}
