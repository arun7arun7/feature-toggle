package com.example.featuretoggle.service.handler;

import com.example.featuretoggle.constant.CustomAttributesOperator;
import com.example.featuretoggle.constant.ErrorCode;
import com.example.featuretoggle.exception.ApiException;
import com.example.featuretoggle.service.CustomAttributesOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomOperatorFactory {

    @Autowired
    private List<CustomAttributesOperatorService> serviceList;

    private Map<CustomAttributesOperator, CustomAttributesOperatorService> map = new HashMap<>();

    @PostConstruct
    private void init() {
        for(CustomAttributesOperatorService customAttributesOperatorService : serviceList) {
            map.put(customAttributesOperatorService.getCustomAttributesOperation(), customAttributesOperatorService);
        }
    }

    public CustomAttributesOperatorService getService(CustomAttributesOperator customAttributesOperator) {
        if(map.containsKey(customAttributesOperator)) {
            return map.get(customAttributesOperator);
        }
        else {
            throw new ApiException(ErrorCode.UNKNOWN_OPERATOR_FOR_FEATURE_EVALUATION);
        }
    }
}
