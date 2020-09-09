package com.example.featuretoggle.service.impl;

import com.example.featuretoggle.constant.CustomAttributesOperator;
import com.example.featuretoggle.service.CustomAttributesOperatorService;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
public class GreaterThanOrEqualToOperatorService implements CustomAttributesOperatorService {

    @Override
    public boolean evaluate(JsonElement attributeValue, JsonElement dbValue) {
        if (!attributeValue.isJsonPrimitive() || !attributeValue.getAsJsonPrimitive().isNumber()) {
            return false;
        }
        Number value = attributeValue.getAsJsonPrimitive().getAsNumber();
        NumberComparator numberComparator = new NumberComparator();
        if (numberComparator.compare(value, dbValue.getAsNumber()) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public CustomAttributesOperator getCustomAttributesOperation() {
        return CustomAttributesOperator.GREATER_THAN_OR_EQUAL_TO;
    }

    class NumberComparator implements Comparator<Number> {

        public int compare(Number a, Number b){
            return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
        }

    }
}
