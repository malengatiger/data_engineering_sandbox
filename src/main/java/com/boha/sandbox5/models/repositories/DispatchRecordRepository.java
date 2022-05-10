package com.boha.sandbox5.models.repositories;

import com.boha.sandbox5.models.City;
import com.boha.sandbox5.models.DispatchRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DispatchRecordRepository extends MongoRepository<DispatchRecord, String> {

}
