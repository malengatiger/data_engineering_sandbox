package com.boha.sandbox5.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("routedistanceestimation")
public class RouteDistanceEstimation {
    @Id
    String id;
    String routeID, routeName, nearestLandmarkName, nearestLandmarkID, type;
    List<DynamicDistance> dynamicDistances;
    double distanceToNearestLandmark;
    String created;
    Vehicle vehicle;
}
