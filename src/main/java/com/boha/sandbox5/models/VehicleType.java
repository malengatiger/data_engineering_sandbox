package com.boha.sandbox5.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("vehicletype")
public class VehicleType {
    int capacity;
    @Id
    String vehicleTypeID;
    String make;
    String model;
    String countryID;
}
