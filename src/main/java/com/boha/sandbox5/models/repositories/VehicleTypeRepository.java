package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.Vehicle;
import com.boha.sandbox5.models.VehicleType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleTypeRepository extends MongoRepository<VehicleType, String> {

}
