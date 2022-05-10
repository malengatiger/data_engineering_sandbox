package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.DispatchRecord;
import com.boha.sandbox5.models.VehicleLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleLocationRepository extends MongoRepository<VehicleLocation, String> {
    
}
