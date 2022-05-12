package com.boha.sandbox5.services;

import com.boha.sandbox5.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.BsonValue;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PublisherService {
    static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    static final String mx = "\uD83C\uDD7F️ \uD83C\uDD7F️ \uD83C\uDD7F️ ";
    static final String my = "\uD83C\uDF4E ";
    static final String mm = "☘️ ☘️ ☘️ PublisherService: ";
    static Random random = new Random(System.currentTimeMillis());

    public PublisherService() {
        LOGGER.info(mm + "constructor - PublisherService ready to start ....");
    }

    @Autowired
    DataService dataService;
    @Autowired
    MongoClient mongoClient;

    @Autowired
    private DataTransferGateway dataTransferGateway;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    MongoDatabase database;


    @MessagingGateway(defaultRequestChannel = "dataTransferChannel")

    public interface DataTransferGateway {
        void sendToPubsub(String text);
    }

    public List<Landmark> getLandmarks() {
        List<Landmark> list = dataService.getLandmarks();
        LOGGER.info("Landmarks found: " + list.size());
        return list;
    }

    public boolean publishLandmarks() throws Exception {
        List<Landmark> list = getLandmarks();
        for (Landmark m : list) {
            LOGGER.info(mx +
                    "Sending to PubSub landmark: " + my +
                    " \uD83C\uDF4E : " + m.getLandmarkName()
                    + " id " + m.getLandmarkID());

            dataTransferGateway.sendToPubsub(G.toJson(m));
            LOGGER.info(mx + "Landmark sent to PubSub: " + m.getLandmarkName() + " cities around landmark: " + m.getCities().size());
            int secondsToSleep = random.nextInt(10);
            if (secondsToSleep < 1) secondsToSleep = 3;
            LOGGER.info(mx + " ...zzzzzzz sleeping for " + secondsToSleep + " seconds");
            TimeUnit.SECONDS.sleep(secondsToSleep);

        }
        return true;
    }

    static final String zz = "🔴 🔴 🔴 🔴";
    private String getName(String string) {
        //BsonString{value='routedistanceestimations'}
        int i = string.indexOf("'");
        String a = string.substring(i+1);
        return a.replace("'}","");
    }


}


