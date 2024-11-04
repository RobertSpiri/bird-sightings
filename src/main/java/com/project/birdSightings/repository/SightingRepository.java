package com.project.birdSightings.repository;

import com.project.birdSightings.model.Bird;
import com.project.birdSightings.model.Sighting;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Date;

public interface SightingRepository extends ReactiveMongoRepository<Sighting, String> {
    Flux<Sighting> findByBird(Bird bird);

    Flux<Sighting> findByLocation(String location);

    @Query("{ 'date' : { $gte: ?0, $lte: ?1 } }")
    Flux<Sighting> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
