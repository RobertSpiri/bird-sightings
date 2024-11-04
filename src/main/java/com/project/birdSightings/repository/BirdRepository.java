package com.project.birdSightings.repository;

import com.project.birdSightings.model.Bird;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BirdRepository extends ReactiveMongoRepository<Bird, String> {
    Flux<Bird> findByColor(String color);
}