package com.boha.sandbox5.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("vehicledepartures")
public class VehicleDeparture {
    @Id
    String vehicleDepartureID;
    String landmarkID;
    String landmarkName;
    String routeName;
    String routeID;
    String vehicleID;
    String associationID;
    String associationName;
    String vehicleReg;
    String created, type;
    VehicleType vehicleType;
    Position position;
}
