package com.exercise.api.data.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class ControllerTest {
    @Value("${server.protocol}")
    String hostScheme;

    @Value("${server.address}")
    String hostAddress;

    @Value("${server.port}")
    String hostPort;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected URI buildURI(Map<String, String> template, String ...path) throws URISyntaxException {
        return  UriComponentsBuilder.newInstance()
                .scheme(hostScheme)
                .host(hostAddress)
                .port(hostPort)
                .pathSegment(path)
                .buildAndExpand(template)
                .toUri();
    }

    protected URI buildURI(String ...path) throws URISyntaxException {
        return buildURI(new HashMap<String, String>(), path);
    }
}
