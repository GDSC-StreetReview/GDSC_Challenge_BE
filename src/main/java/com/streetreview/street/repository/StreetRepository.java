package com.streetreview.street.repository;

import com.streetreview.street.entity.Street;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StreetRepository extends MongoRepository<Street, String> {
    Optional<Street> findByXAndY(Double X, Double Y);
    Optional<Street> findByStreetId(String streetId);;
}
