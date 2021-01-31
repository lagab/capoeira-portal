package com.lagab.capoeira.capoeiraportal.errors;

import com.lagab.capoeira.capoeiraportal.web.rest.errors.BadRequestAlertException;
import com.lagab.capoeira.capoeiraportal.web.rest.errors.ErrorConstants;

public class InfiniteLoopException extends BadRequestAlertException {
    public InfiniteLoopException(String entity, String message) {
        super(ErrorConstants.INFINITE_LOOP_TYPE, message, entity, "infinite_loop");
    }

}
