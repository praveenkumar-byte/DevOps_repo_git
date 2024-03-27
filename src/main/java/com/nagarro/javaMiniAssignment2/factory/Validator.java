package com.nagarro.javaMiniAssignment2.factory;

import com.nagarro.javaMiniAssignment2.exception.UserCustomException;

import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.*;

public interface Validator {

     default void validateNumericLimit(int limit) throws UserCustomException {
        if (limit < 1 || limit > 5) {
            throw new UserCustomException(INVALID_LIMIT_VALUE);
        }
    }


 default void validateNumericOffset(int offset) throws UserCustomException {
        if (offset < 0) {
            throw new UserCustomException(INVALID_OFFSET_VALUE);
        }
    }

     default void validateAlphabet(String input) throws UserCustomException {
        if (!SORT_BY_NAME.equalsIgnoreCase(input) && !SORT_BY_AGE.equalsIgnoreCase(input)
                && !SORT_ORDER_EVEN.equalsIgnoreCase(input) && !SORT_ORDER_ODD.equalsIgnoreCase(input)) {
            throw new UserCustomException(INVALID_SORT_TYPE_ORDER);
        }
    }
}
