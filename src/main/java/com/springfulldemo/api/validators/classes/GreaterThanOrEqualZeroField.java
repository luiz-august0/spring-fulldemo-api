package com.springfulldemo.api.validators.classes;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class GreaterThanOrEqualZeroField extends ValidationField {

    public GreaterThanOrEqualZeroField(Field field, String portugueseField) {
        super(field, portugueseField);
    }

}