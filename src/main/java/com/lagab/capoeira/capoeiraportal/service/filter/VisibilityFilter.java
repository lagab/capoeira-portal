package com.lagab.capoeira.capoeiraportal.service.filter;

import com.lagab.capoeira.capoeiraportal.domain.enums.Visibility;

public class VisibilityFilter extends Filter<Visibility> {
    private static final long serialVersionUID = 1L;

    public VisibilityFilter() {
    }

    public VisibilityFilter(VisibilityFilter filter) {
        super(filter);
    }

    public VisibilityFilter copy() {
        return new VisibilityFilter(this);
    }
}

