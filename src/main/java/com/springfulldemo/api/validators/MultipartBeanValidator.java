package com.springfulldemo.api.validators;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.model.beans.MultipartBean;
import com.springfulldemo.api.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class MultipartBeanValidator extends AbstractValidator<MultipartBean> {
    public MultipartBeanValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(MultipartBean.class.getDeclaredField("file"), "arquivo"));
            requiredFields.add(new RequiredField(MultipartBean.class.getDeclaredField("filename"), "nome do arquivo"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}