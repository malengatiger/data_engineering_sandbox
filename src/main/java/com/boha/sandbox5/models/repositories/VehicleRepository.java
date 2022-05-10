package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.Landmark;
import com.boha.sandbox5.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

}
