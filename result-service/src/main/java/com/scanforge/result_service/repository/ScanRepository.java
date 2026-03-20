package com.scanforge.result_service.repository;

import com.scanforge.result_service.model.Scan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends MongoRepository<Scan, String> {
}
