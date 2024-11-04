package com.project.birdSightings.controller;

import com.project.birdSightings.model.Bird;
import com.project.birdSightings.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing birds.
 */
@RestController
@RequestMapping("/api/bird")
public class BirdController {

    @Autowired
    private BirdService birdService;

    /**
     * Adds a new bird.
     *
     * @param bird the bird to add
     * @return a Mono containing the added bird
     */
    @PostMapping
    public Mono<Bird> addBird(@RequestBody Bird bird) {
        return birdService.addBird(bird);
    }

    /**
     * Retrieves all birds.
     *
     * @return a Flux containing all birds
     */
    @GetMapping
    public Flux<Bird> getAllBirds() {
        return birdService.getAllBirds();
    }

    /**
     * Retrieves a bird by its name.
     *
     * @param name the name of the bird
     * @return a Mono containing the bird with the specified name
     */
    @GetMapping("/name/{name}")
    public Mono<Bird> getBirdByName(@PathVariable String name) {
        return birdService.findBirdByName(name);
    }

    /**
     * Retrieves birds by their color.
     *
     * @param color the color of the birds
     * @return a Flux containing the birds with the specified color
     */
    @GetMapping("/color/{color}")
    public Flux<Bird> getBirdByColor(@PathVariable String color) {
        return birdService.findBirdByColor(color);
    }

    /**
     * Deletes a bird by its name.
     *
     * @param name the name of the bird to delete
     * @return a Mono indicating when the deletion is complete
     */
    @DeleteMapping("/{name}")
    public Mono<Void> deleteBird(@PathVariable String name) {
        return birdService.deleteBird(name);
    }
}