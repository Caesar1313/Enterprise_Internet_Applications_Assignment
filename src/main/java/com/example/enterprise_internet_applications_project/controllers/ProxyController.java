package com.example.enterprise_internet_applications_project.controllers;


import com.example.enterprise_internet_applications_project.aspects.LoggingAspect;
import com.example.enterprise_internet_applications_project.services.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
public class ProxyController {
    Logger logger = LoggerFactory.getLogger(ProxyController.class);
    @Autowired
    ProxyService service;

    @RequestMapping("/**")
    public ResponseEntity<String> sendRequestToSPM(@RequestBody(required = false) String body,
                                                   HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        logger.debug("Proxy receive a request  ..");
        return service.processProxyRequest(body,method,request,response, UUID.randomUUID().toString());
    }
}
