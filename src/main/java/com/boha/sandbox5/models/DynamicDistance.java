package com.boha.sandbox5.models;
import lombok.Data;

@Data
public class DynamicDistance {
    double distanceInMetres, distanceInKM;
    String landmarkName, landmarkID, date, routeName;
}
