package com.project.birdSightings.service;

import com.project.birdSightings.errorHandling.ResourceNotFoundException;
import com.project.birdSightings.model.Bird;
import com.project.birdSightings.model.Sighting;
import com.project.birdSightings.repository.BirdRepository;
import com.project.birdSightings.repository.SightingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Service class for managing bird sightings.
 */
@Service
public class SightingService {
    @Autowired
    private SightingRepository sightingRepository;

    @Autowired
    private BirdRepository birdRepository;

    /**
     * Adds a new sighting.
     * If the bird referenced in the sighting does not exist, an error is thrown.
     * Side note: I could have gone 2 ways with this. I could have added the bird to the database
     * if it didn't exist, or I could have thrown an error. I chose to throw an error.
     *
     * @param sighting the sighting to add
     * @return a Mono containing the added sighting wrapped in a ResponseEntity
     */
    public Mono<ResponseEntity<Sighting>> addSighting(Sighting sighting) {
        return birdRepository.findById(sighting.getBird().getName())
            .flatMap(existingBird -> {
                // Set the found bird reference to the sighting
                sighting.setBird(existingBird);
                return sightingRepository.save(sighting)
                    .map(savedSighting -> ResponseEntity.status(HttpStatus.CREATED).body(savedSighting));
            })
            .switchIfEmpty(
                Mono.error(new ResourceNotFoundException("Bird not found. Cannot add sighting."))
            );
    }

    /**
     * Retrieves all sightings.
     *
     * @return a Flux containing all sightings
     */
    public Flux<Sighting> getAllSightings() {
        return sightingRepository.findAll();
    }

    /**
     * Retrieves sightings by a specific bird.
     *
     * @param bird the bird to filter sightings by
     * @return a Flux containing the sightings of the specified bird
     */
    public Flux<Sighting> getSightingsByBird(Bird bird) {
        return sightingRepository.findByBird(bird)
            .switchIfEmpty(
                Mono.error(
                    new ResourceNotFoundException(
                        MessageFormat.format(
                            "Sighting not found. Sighting for bird with name {0} does not exist.",
                            bird.getName()
                        )
                    )
                )
            );
    }

    /**
     * Retrieves sightings by location.
     *
     * @param location the location to filter sightings by
     * @return a Flux containing the sightings at the specified location
     */
    public Flux<Sighting> getSightingsByLocation(String location) {
        return sightingRepository.findByLocation(location)
            .switchIfEmpty(
                Mono.error(
                    new ResourceNotFoundException(
                        MessageFormat.format(
                            "Sighting not found. Sighting for location {0} does not exist.",
                            location
                        )
                    )
                )
            );
    }

    /**
     * Retrieves sightings within a date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a Flux containing the sightings within the specified date range
     */
    public Flux<Sighting> getSightingsByDateBetween(LocalDate startDate, LocalDate endDate) {
        return sightingRepository.findByDateBetween(startDate, endDate)
            .switchIfEmpty(
                Mono.error(
                    new ResourceNotFoundException(
                        MessageFormat.format(
                            "Sighting not found. Sighting between {0} and {1} does not exist.",
                            startDate,
                            endDate
                        )
                    )
                )
            );
    }

    /**
     * Deletes a sighting by its ID.
     *
     * @param id the ID of the sighting to delete
     * @return a Mono indicating when the deletion is complete
     */
    public Mono<Void> deleteSighting(String id) {
        return sightingRepository.deleteById(id);
    }
}