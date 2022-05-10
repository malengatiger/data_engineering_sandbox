package com.boha.sandbox5.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ActuatorLogger {
    public static final Logger LOGGER = Logger.getLogger(ActuatorLogger.class.getName());

//    public ActuatorLogger(@Autowired PathMappedEndpoints pme) {
//        LOGGER.info("\uD83D\uDD35 \uD83D\uDD35 \uD83D\uDD35 Actuator base path: " + pme.getBasePath());
//        pme.getAllPaths().forEach(p -> LOGGER.info("\uD83D\uDD35 Path: " +  p));
//    }
}