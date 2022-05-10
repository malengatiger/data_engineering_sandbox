package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository  extends MongoRepository<City, String> {
    City findByName(String name);
}
