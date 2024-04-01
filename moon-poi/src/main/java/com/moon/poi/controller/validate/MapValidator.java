package com.moon.poi.controller.validate;

import org.apache.commons.collections4.MapUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class MapValidator implements ConstraintValidator<MapCheck, Map<String, Object>> {
    @Override
    public boolean isValid(Map<String, Object> map, ConstraintValidatorContext context) {
        return MapUtils.isNotEmpty(map);
    }
}
