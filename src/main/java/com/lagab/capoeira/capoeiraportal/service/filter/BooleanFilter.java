package com.lagab.capoeira.capoeiraportal.service.filter;

public class BooleanFilter extends Filter<Boolean> {
    private static final long serialVersionUID = 1L;

    public BooleanFilter() {
    }

    public BooleanFilter(BooleanFilter filter) {
        super(filter);
    }

    public BooleanFilter copy() {
        return new BooleanFilter(this);
    }
}
