package com.nagarro.javaMiniAssignment2.factory.Validators;


import com.nagarro.javaMiniAssignment2.exception.UserCustomException;
import com.nagarro.javaMiniAssignment2.factory.Validator;

import static com.nagarro.javaMiniAssignment2.utility.constants.Constants.*;

public class EnglishAlphabetsValidator implements Validator {
    private static final EnglishAlphabetsValidator INSTANCE = new EnglishAlphabetsValidator();

    private EnglishAlphabetsValidator() {
    }

    public static EnglishAlphabetsValidator getInstance() {
        return INSTANCE;
    }



}
