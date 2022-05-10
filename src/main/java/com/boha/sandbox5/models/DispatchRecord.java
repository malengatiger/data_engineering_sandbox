package com.boha.sandbox5.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("dispatchrecords")
public class DispatchRecord {
    @Id
    String dispatchRecordID;
    String landmarkID;
    String marshalID;
    int passengers;
    VehicleType vehicleType;
    String ownerID;
    String created;
    Position position;
    String landmarkName, marshalName;
    String routeName;
    String routeID;
    String vehicleID, vehicleArrivalID;
    String vehicleReg, associationD, associationName, type;
    boolean dispatched;


}
