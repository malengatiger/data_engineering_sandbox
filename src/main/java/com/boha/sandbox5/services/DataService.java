package com.boha.sandbox5.services;

import com.boha.sandbox5.models.Association;
import com.boha.sandbox5.models.Landmark;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DataService {
    private static final Logger LOGGER = Logger.getLogger(DataService.class.getName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private static final String mm = "🔵 🔵 🔵 🔵 🔵 ", nn = " \uD83D\uDD36 \uD83D\uDD36 \uD83D\uDD36 \uD83D\uDD36 ";
    @Autowired
    private MongoClient mongoClient;
    private MongoDatabase db;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;
    public DataService() {
        LOGGER.info(mm + " DataService constructed " + mm);

    }
    public void initialize() {
        if (databaseName != null) {
            db = mongoClient.getDatabase(databaseName);
            LOGGER.info(mm + " DataService initialize: database initialized OK!: "
                    + mm + " " + databaseName + " " + mm);
            LOGGER.info(mm + " DataService ready to rumble: " + " " + mm);
        } else {
            LOGGER.info(mm + " DataService initialize: mongoDB database name not available");
        }
    }
    private  FindIterable<Document> getDocuments(String collectionName) {
        MongoCollection<Document> col = db.getCollection(collectionName);
        return col.find();
    }
    public List<Landmark> getLandmarks() {
        List<Landmark> marks = new ArrayList<>();
        FindIterable<Document> docs =  getDocuments("landmarks");
        int cnt = 0;
        int totalCities = 0;

        for (Document doc : docs) {
            Landmark mark = G.fromJson(doc.toJson(), Landmark.class);
            marks.add(mark);
            cnt++;
            totalCities +=  mark.getCities().size();
        }
        LOGGER.info(nn + " Landmarks found: " + cnt);
        LOGGER.info(nn + " Average Cities per Landmarks: " + (totalCities/cnt));
        return marks;
    }
    public List<Association> getAssociations() {
        List<Association> associations = new ArrayList<>();
        FindIterable<Document> docs =  getDocuments("associations");

        for (Document doc : docs) {
            Association mark = G.fromJson(doc.toJson(), Association.class);
            associations.add(mark);
        }
        LOGGER.info(nn + " Associations found: " + associations.size());
        return associations;
    }

}
