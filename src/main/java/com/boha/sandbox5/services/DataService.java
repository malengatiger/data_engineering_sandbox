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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DataService {
    private static final Logger LOGGER = Logger.getLogger(DataService.class.getName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private static final String mm = "🔵 🔵 🔵", nn = " \uD83D\uDD36 \uD83D\uDD36 \uD83D\uDD36 \uD83D\uDD36";
    @Autowired
    private MongoClient mongoClient;
    private MongoDatabase db;
    public DataService() {
        LOGGER.info(mm + " DataService constructed " + mm);

    }
    public List<Landmark> getLandmarks() {
       setDatabase();
        MongoCollection<Document> col = db.getCollection("landmarks");
        FindIterable<Document> docs =  col.find();
        LOGGER.info(nn + " Landmarks Listing: ");
        int cnt = 0;
        int totalCities = 0;
        List<Landmark> marks = new ArrayList<>();
        for (Document doc : docs) {
            Landmark mark = G.fromJson(doc.toJson(), Landmark.class);
            LOGGER.info("\uD83D\uDD36 \uD83D\uDD36 Landmark: #" + (cnt+1) + " " + mark.getLandmarkName()
                    + " \uD83C\uDD7F️ cities: " + mark.getCities().size());
            marks.add(mark);
            cnt++;
            totalCities +=  mark.getCities().size();
        }
        LOGGER.info(nn + "  Landmarks found: " + cnt);
        LOGGER.info(nn + "  Average Cities per Landmarks: " + (totalCities/cnt));
        return marks;
    }
    public List<Association> getAssociations() {
       setDatabase();
        MongoCollection<Document> col = db.getCollection("associations");
        FindIterable<Document> docs =  col.find();
        LOGGER.info(nn + "  Association Listing: ");
        int cnt = 0;
        List<Association> marks = new ArrayList<>();
        for (Document doc : docs) {
            Association mark = G.fromJson(doc.toJson(), Association.class);
            LOGGER.info("\uD83D\uDD36 \uD83D\uDD36 Association: #" + (cnt+1)
                    + " " + mark.getAssociationName());
            marks.add(mark);
            cnt++;
        }
        LOGGER.info(nn + "  Landmarks found: " + marks.size());
        return marks;
    }
    private boolean setDatabase() {
        if (db == null) {
            db = mongoClient.getDatabase("ardb");
            return true;
        }
        return false;
    }
}
