package com.boha.sandbox5.controllers;

import com.boha.sandbox5.Sandbox5Application;
import com.boha.sandbox5.models.Association;
import com.boha.sandbox5.models.City;
import com.boha.sandbox5.models.Landmark;
import com.boha.sandbox5.models.repositories.CityRepository;
import com.boha.sandbox5.services.DataService;
import com.boha.sandbox5.services.PublisherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private static final String mm = "\uD83C\uDF50 \uD83C\uDF50 \uD83C\uDF50 ";
    private static final String nn = "\uD83E\uDD6C \uD83E\uDD6C \uD83E\uDD6C ";
    @Autowired
    private DataService dataService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/")
    private ResponseEntity<Object> heita() {
        return ResponseEntity.ok("Sandbox Application says: " +
                "\uD83E\uDD6C \uD83E\uDD6C \uD83E\uDD6C HELLO WORLD at " + new Date().toString());
    }
    @GetMapping("/tell")
    private ResponseEntity<Object> tell() {
        return ResponseEntity.ok("Sandbox Application tells you: " +
                "\uD83E\uDD6C \uD83E\uDD6C \uD83E\uDD6C telling you: HELLO WORLD at " + new Date().toString());
    }

    @GetMapping("/publishLandmarks")
    private ResponseEntity<Object> publishLandmarks() throws Exception {
        boolean published = publisherService.publishLandmarks();
        return ResponseEntity.ok(published);
    }
    @GetMapping("/check")
    private ResponseEntity<Object> checkData() {
        List<Landmark> marks = dataService.getLandmarks();
        LOGGER.info(mm + "  Landmarks found: " + marks.size());
        int totalLandmarks = 0;
        for (Landmark m: marks) {
            totalLandmarks +=  m.getCities().size();
        }
        if (!marks.isEmpty()) {
            LOGGER.info(mm + "  " +
                    "Average number of Cities per Landmark: " + (totalLandmarks / marks.size()));
        }

        List<Association> asses = dataService.getAssociations();
        LOGGER.info(nn + nn + "  Associations found: " + asses.size());

        Bag bag = new Bag(asses, marks);
        return ResponseEntity.ok(bag);
    }
    @Data
    static class Bag {
        List<Association> asses;
        List<Landmark> marks;
        public Bag(List<Association> asses, List<Landmark> marks) {
            this.asses = asses;
            this.marks = marks;
        }
    }
}
