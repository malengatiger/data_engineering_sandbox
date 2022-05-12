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


    ChangeStreamIterable<Document> publisher;

    public void listenForDatabaseChanges() {
        LOGGER.info(mm + "\uD83D\uDECE \uD83D\uDECE listenForDatabaseChanges on MongoDB database ...");
        try {
            database = mongoClient.getDatabase(databaseName);
            publisher = database.watch();
            LOGGER.info(mm + "\uD83D\uDECE \uD83D\uDECE listening to this database: " + database.getName());
            for (ChangeStreamDocument<Document> doc : publisher) {
                Document mDoc = doc.getFullDocument();
                assert mDoc != null;
                assert doc.getNamespaceDocument() != null;
                BsonValue value = doc.getNamespaceDocument().get("coll");
                String name = value.toString();
                name = getName(name);

                switch (name) {
                    case "vehiclelocations":
                        VehicleLocation vl = G.fromJson(mDoc.toJson(), VehicleLocation.class);
                        vl.setType(Constants.VEHICLE_LOCATION);
                        LOGGER.info(mx +
                                "A vehicleLocation:  " + my + vl.getVehicleReg() + " "
                                + vl.getVehicleType().getMake() + " " + vl.getVehicleType().getModel());
                        dataTransferGateway.sendToPubsub(G.toJson(vl));
                        break;
                    case "commuterrequests":
                        CommuterRequest cr = G.fromJson(mDoc.toJson(), CommuterRequest.class);
                        cr.setType(Constants.COMMUTER_REQUEST);
                        LOGGER.info(mx +
                                "A commuterRequest: " + my +
                                " \uD83C\uDF4E from:" + cr.getFromLandmarkName() + " to: " + cr.getToLandmarkName()
                                + " from user: " + cr.getUserID());
                        dataTransferGateway.sendToPubsub(G.toJson(cr));
                        break;
                    case "routedistanceestimations":
                        RouteDistanceEstimation rde = G.fromJson(mDoc.toJson(), RouteDistanceEstimation.class);
                        rde.setType(Constants.ROUTE_DISTANCE_ESTIMATION);
                        LOGGER.info(mx +
                                "A routeDistanceEstimation " + my + "vehicle: "
                                + rde.getVehicle().getVehicleReg() +
                                " \uD83D\uDD36 route: " + rde.getRouteName());
                        dataTransferGateway.sendToPubsub(G.toJson(rde));
                        break;
                    case "dispatchrecords":
                        DispatchRecord dr = G.fromJson(mDoc.toJson(), DispatchRecord.class);
                        dr.setType(Constants.DISPATCH_RECORD);
                        LOGGER.info(mx +
                                "A DispatchRecord: " + my +
                                " vehicle: " + dr.getVehicleReg() + " from landmark: " + dr.getLandmarkName()
                                + " on route: " + dr.getRouteName());
                        dataTransferGateway.sendToPubsub(G.toJson(dr));
                        break;
                    case "vehiclearrivals":
                        VehicleArrival va = G.fromJson(mDoc.toJson(), VehicleArrival.class);
                        va.setType(Constants.VEHICLE_LANDMARK_ARRIVAL);
                        LOGGER.info(mx +
                                "A VehicleArrival: " + my +
                                " vehicle: " + va.getVehicleReg() + " at landmark: " + va.getLandmarkName());
                        dataTransferGateway.sendToPubsub(G.toJson(va));
                        break;
                    case "vehicledepartures":
                        VehicleDeparture vd = G.fromJson(mDoc.toJson(), VehicleDeparture.class);
                        vd.setType(Constants.VEHICLE_LANDMARK_DEPARTURE);
                        LOGGER.info(mx +
                                "A VehicleDeparture: " + my +
                                " vehicle: " + vd.getVehicleReg() + " departing landmark: " + vd.getLandmarkName());
                        dataTransferGateway.sendToPubsub(G.toJson(vd));
                        break;
                    default:
                        LOGGER.info(mm + "\uD83D\uDECE \uD83D\uDECE some non-essential fucker came thru: ..."
                                +" has been IGNORED! : " + name);
                        break;
                }

            }

        } catch (Exception e) {
            LOGGER.info(mm + zz +
                    "Hey Boss, we fell down on the transfer!! " + e.toString() + " \uD83D\uDD34");
            LOGGER.info(mm + zz + " starting databaseListen again????");
            listenForDatabaseChanges();

        }

    }
    static final String zz = "🔴 🔴 🔴 🔴";
    private String getName(String string) {
        //BsonString{value='routedistanceestimations'}
        int i = string.indexOf("'");
        String a = string.substring(i+1);
        return a.replace("'}","");
    }


}


