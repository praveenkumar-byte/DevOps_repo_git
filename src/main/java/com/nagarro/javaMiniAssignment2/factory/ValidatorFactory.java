package com.nagarro.javaMiniAssignment2.factory;

import com.nagarro.javaMiniAssignment2.factory.Validators.EnglishAlphabetsValidator;
import com.nagarro.javaMiniAssignment2.factory.Validators.NumericValidator;

public class ValidatorFactory {
    public static Validator getValidator(Object value) {
        if (value instanceof Integer) {
            return NumericValidator.getInstance();
        } else if (value instanceof String) {
            return EnglishAlphabetsValidator.getInstance();
        }
        throw new IllegalArgumentException("Unsupported validator type.");
    }
}