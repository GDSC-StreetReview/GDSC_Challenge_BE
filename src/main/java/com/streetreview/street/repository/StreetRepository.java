package com.streetreview.street.repository;

import com.streetreview.street.entity.Street;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StreetRepository extends MongoRepository<Street, String> {
    Optional<Street> findByLocation(GeoJsonPoint location);
    Optional<Street> findById(String streetId);

    @Query(value = "{ 'location': { $near: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } } }", sort = "{'location': 1}")
    List<Street> findNear(double latitude, double longitude, double maxDistance);

    List<Street> findAll();
}
