package com.boha.sandbox5.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("landmarks")
public class Landmark {
    @Id
    String landmarkID;
    double latitude;
    double longitude;
    double distance;
    String landmarkName;
    List<RouteInfo> routeDetails;
    List<City> cities;
    Position position;
}
