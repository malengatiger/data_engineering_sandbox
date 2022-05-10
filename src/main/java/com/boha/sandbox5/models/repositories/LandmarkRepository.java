package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.CommuterRequest;
import com.boha.sandbox5.models.Landmark;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LandmarkRepository extends MongoRepository<Landmark, String> {

}
