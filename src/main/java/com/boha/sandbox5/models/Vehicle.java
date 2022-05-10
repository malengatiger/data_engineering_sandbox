package com.boha.sandbox5.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("vehicles")
public class Vehicle {
    @Id
    String vehicleID;
    String ownerID;
    String associationID;
    String ownerName;
    String associationName;
    String created;

    VehicleType vehicleType;
    String vehicleReg;

}
