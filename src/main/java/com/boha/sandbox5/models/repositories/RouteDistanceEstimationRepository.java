package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.DispatchRecord;
import com.boha.sandbox5.models.RouteDistanceEstimation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteDistanceEstimationRepository extends MongoRepository<RouteDistanceEstimation, String> {
    
}
