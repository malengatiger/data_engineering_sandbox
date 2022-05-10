package com.boha.sandbox5.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("commuterrequests")
public class CommuterRequest {
    @Id
    String commuterRequestID;
    String toLandmarkName, fromLandmarkName;
    String toLandmarkID, fromLandmarkID;
    City originCity, destinationCity;
    int passengers;
    String stringTime;
    String userID;
    String created;
    boolean scanned;
    boolean autoDetected, isWallet;
    String expiredDate;
    double fare;
    Position position;
    String routeName;
    String routeID;
    String vehicleID, fcmToken;
    String vehicleReg, associationID, associationName, type;


}
