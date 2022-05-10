package com.boha.sandbox5.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("vehiclearrivals")
public class VehicleArrival {
    @Id
    String vehicleArrivalID;
    String landmarkID;
    String landmarkName;
    Position position;
    String created;
    String vehicleID;
    String associationID;
    String associationName;
    String vehicleReg, type;
    VehicleType vehicleType;
    boolean dispatched;
}
