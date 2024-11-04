package com.project.birdSightings.service;

import com.project.birdSightings.errorHandling.ResourceNotFoundException;
import com.project.birdSightings.model.Bird;
import com.project.birdSightings.repository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

/**
 * Service class for managing birds.
 */
@Service
public class BirdService {
    @Autowired
    private BirdRepository birdRepository;

    /**
     * Adds a new bird.
     *
     * @param bird the bird to add
     * @return a Mono containing the added bird
     */
    public Mono<Bird> addBird(Bird bird) {
        return birdRepository.save(bird);
    }

    /**
     * Retrieves all birds.
     *
     * @return a Flux containing all birds
     */
    public Flux<Bird> getAllBirds() {
        return birdRepository.findAll();
    }

    /**
     * Finds a bird by its name.
     *
     * @param name the name of the bird
     * @return a Mono containing the bird with the specified name, or an error if not found
     */
    public Mono<Bird> findBirdByName(String name) {
        return birdRepository.findById(name)
            .switchIfEmpty(
                Mono.error(
                    new ResourceNotFoundException(
                        MessageFormat.format(
                            "Bird not found. Bird with name {0} does not exist.", name
                        )
                    )
                )
            );
    }

    /**
     * Finds birds by their color.
     *
     * @param color the color of the birds
     * @return a Flux containing the birds with the specified color, or an error if not found
     */
    public Flux<Bird> findBirdByColor(String color) {
        return birdRepository.findByColor(color)
            .switchIfEmpty(
                Mono.error(
                    new ResourceNotFoundException(
                        MessageFormat.format(
                            "Bird not found. Bird with color {0} does not exist.", color
                        )
                    )
                )
            );
    }

    /**
     * Deletes a bird by its ID.
     *
     * @param id the ID of the bird to delete
     * @return a Mono indicating when the deletion is complete
     */
    public Mono<Void> deleteBird(String id) {
        return birdRepository.deleteById(id);
    }
}