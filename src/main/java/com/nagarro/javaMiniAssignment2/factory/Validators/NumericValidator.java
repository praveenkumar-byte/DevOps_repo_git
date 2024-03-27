package com.nagarro.javaMiniAssignment2.factory.Validators;
import com.nagarro.javaMiniAssignment2.factory.Validator;
public class NumericValidator implements Validator {
    private static final NumericValidator INSTANCE = new NumericValidator();

    private NumericValidator() {}

    public static NumericValidator getInstance() {
        return INSTANCE;
    }


}
