package com.training.pet.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EndpointController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/all-endpoints")
    public List<String> getAllEndpoints() {

        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();

        List<String> endpoints = new ArrayList<>();

        for (RequestMappingInfo info : map.keySet()) {

            if (info.getPathPatternsCondition() != null) {
                info.getPathPatternsCondition()
                        .getPatternValues()
                        .forEach(endpoints::add);
            }
        }

        return endpoints;
    }
}
