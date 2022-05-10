package com.boha.sandbox5.models;

import lombok.Data;

@Data
public class RoutePoint {
    double latitude;
    double longitude;
    double heading;
    int index;
    String created, routeID;
    String landmarkID, landmarkName;
    Position position;
}
