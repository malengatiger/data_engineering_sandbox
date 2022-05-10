package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.City;
import com.boha.sandbox5.models.CommuterRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommuterRequestRepository extends MongoRepository<CommuterRequest, String> {

}
