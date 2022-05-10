package com.boha.sandbox5.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cities")
@Data

public class City {
    @Id
    public String id;
    String cityID;
    String countryID;
    String name, distance;
    String provinceName;
    double latitude;
    double longitude;
    String countryName;
    Position position;
    Object geo;

    public City(String cityID, String countryID, String name,
                String distance, String provinceName,
                double latitude, double longitude, String countryName, Position position, Object geo) {
        this.cityID = cityID;
        this.countryID = countryID;
        this.name = name;
        this.distance = distance;
        this.provinceName = provinceName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.position = position;
        this.geo = geo;
    }
}
