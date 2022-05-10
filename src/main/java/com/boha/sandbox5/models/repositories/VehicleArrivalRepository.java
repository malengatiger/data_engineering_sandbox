package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.Landmark;
import com.boha.sandbox5.models.VehicleArrival;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleArrivalRepository extends MongoRepository<VehicleArrival, String> {

}
