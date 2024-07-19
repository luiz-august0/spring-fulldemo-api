package com.springfulldemo.api.validators.classes;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class CharacterLengthField extends ValidationField {

    private Integer value;

    private Boolean max;

    public CharacterLengthField(Field field, Integer value, Boolean max, String portugueseField) {
        super(field, portugueseField);
        this.value = value;
        this.max = max;
    }

}