package com.lagab.capoeira.capoeiraportal.service.dto;

import com.lagab.capoeira.capoeiraportal.service.filter.LongFilter;
import com.lagab.capoeira.capoeiraportal.service.filter.StringFilter;
import com.lagab.capoeira.capoeiraportal.service.filter.VisibilityFilter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AcademyCriteria implements Serializable {

    private LongFilter id;
    private StringFilter name;
    private VisibilityFilter visibility;
    private LongFilter schoolId;
}
