package com.boha.sandbox5.models;

import lombok.Data;

import java.util.List;

@Data
public class Position {
    String type;
    List<Double> coordinates;
}
