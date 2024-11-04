package com.project.birdSightings.controller;

import com.project.birdSightings.model.Bird;
import com.project.birdSightings.model.Sighting;
import com.project.birdSightings.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * REST controller for managing bird sightings.
 */
@RestController
@RequestMapping("/api/sighting")
public class SightingController {

    @Autowired
    private SightingService sightingService;

    /**
     * Adds a new sighting.
     *
     * @param sighting the sighting to add
     * @return a Mono containing the added sighting wrapped in a ResponseEntity
     */
    @PostMapping
    public Mono<ResponseEntity<Sighting>> addSighting(@RequestBody Sighting sighting) {
        return sightingService.addSighting(sighting);
    }

    /**
     * Retrieves all sightings.
     *
     * @return a Flux containing all sightings
     */
    @GetMapping
    public Flux<Sighting> getAllSightings() {
        return sightingService.getAllSightings();
    }

    /**
     * Retrieves sightings by a specific bird.
     *
     * @param bird the bird to filter sightings by
     * @return a Flux containing the sightings of the specified bird
     */
    @GetMapping("/bird")
    public Flux<Sighting> getSightingsByBird(@RequestBody Bird bird) {
        return sightingService.getSightingsByBird(bird);
    }

    /**
     * Retrieves sightings by location.
     *
     * @param location the location to filter sightings by
     * @return a Flux containing the sightings at the specified location
     */
    @GetMapping("/location/{location}")
    public Flux<Sighting> getSightingsByLocation(@PathVariable String location) {
        return sightingService.getSightingsByLocation(location);
    }

    /**
     * Retrieves sightings within a date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a Flux containing the sightings within the specified date range
     */
    @GetMapping("/date-range")
    public Flux<Sighting> getSightingsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return sightingService.getSightingsByDateBetween(startDate, endDate);
    }

    /**
     * Deletes a sighting by its ID.
     *
     * @param id the ID of the sighting to delete
     * @return a Mono indicating when the deletion is complete
     */
    @DeleteMapping("/{id}")
    public Mono<Void> deleteSighting(@PathVariable String id) {
        return sightingService.deleteSighting(id);
    }
}