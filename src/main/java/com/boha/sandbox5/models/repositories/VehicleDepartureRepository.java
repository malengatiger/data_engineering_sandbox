package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.Landmark;
import com.boha.sandbox5.models.VehicleDeparture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleDepartureRepository extends MongoRepository<VehicleDeparture, String> {

}
