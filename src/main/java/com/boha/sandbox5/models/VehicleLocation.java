package com.boha.sandbox5.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("vehiclelocations")
public class VehicleLocation {
    @Id
    String id;
    String vehicleID;
    String vehicleReg;
    VehicleType vehicleType;
    String associationID, associationName;
    String created, type;
    Position position;
    List<Landmark> nearestLandmarks;
    List<RoutePoint> routePoints;


}
