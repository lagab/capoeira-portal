package com.lagab.capoeira.capoeiraportal.service.filter;

public class IntegerFilter extends RangeFilter<Integer> {
    private static final long serialVersionUID = 1L;

    public IntegerFilter() {
    }

    public IntegerFilter(IntegerFilter filter) {
        super(filter);
    }

    public IntegerFilter copy() {
        return new IntegerFilter(this);
    }
}

