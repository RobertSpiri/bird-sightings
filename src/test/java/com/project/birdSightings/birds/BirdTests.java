package com.project.birdSightings.birds;

import com.project.birdSightings.controller.BirdController;
import com.project.birdSightings.model.Bird;
import com.project.birdSightings.service.BirdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BirdTests {

    @Mock
    private BirdService birdService;

    @InjectMocks
    private BirdController birdController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBirdSuccessfully() {
        Bird bird = new Bird("Sparrow", "Brown", "3.5", "0.5");
        when(birdService.addBird(any(Bird.class))).thenReturn(Mono.just(bird));

        Mono<Bird> result = birdController.addBird(bird);

        StepVerifier.create(result)
            .expectNext(bird)
            .verifyComplete();
    }

    @Test
    void getAllBirdsSuccessfully() {
        Bird bird1 = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Bird bird2 = new Bird("Robin", "Red", "4.5", "0.8");
        when(birdService.getAllBirds()).thenReturn(Flux.just(bird1, bird2));

        Flux<Bird> result = birdController.getAllBirds();

        StepVerifier.create(result)
            .expectNext(bird1)
            .expectNext(bird2)
            .verifyComplete();
    }

    @Test
    void getBirdByNameSuccessfully() {
        Bird bird = new Bird("Sparrow", "Brown", "3.5", "0.5");
        when(birdService.findBirdByName("Sparrow")).thenReturn(Mono.just(bird));

        Mono<Bird> result = birdController.getBirdByName("Sparrow");

        StepVerifier.create(result)
            .expectNext(bird)
            .verifyComplete();
    }

    @Test
    void getBirdByNameNotFound() {
        when(birdService.findBirdByName("NonExistentBird")).thenReturn(Mono.empty());

        Mono<Bird> result = birdController.getBirdByName("NonExistentBird");

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    void getBirdByColorSuccessfully() {
        Bird bird1 = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Bird bird2 = new Bird("Robin", "Red", "4.5", "0.8");
        when(birdService.findBirdByColor("Brown")).thenReturn(Flux.just(bird1, bird2));

        Flux<Bird> result = birdController.getBirdByColor("Brown");

        StepVerifier.create(result)
            .expectNext(bird1)
            .expectNext(bird2)
            .verifyComplete();
    }

    @Test
    void getBirdByColorNotFound() {
        when(birdService.findBirdByColor("NonExistentColor")).thenReturn(Flux.empty());

        Flux<Bird> result = birdController.getBirdByColor("NonExistentColor");

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    void deleteBirdSuccessfully() {
        when(birdService.deleteBird("Sparrow")).thenReturn(Mono.empty());

        Mono<Void> result = birdController.deleteBird("Sparrow");

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    void deleteBirdNotFound() {
        when(birdService.deleteBird("NonExistentBird")).thenReturn(Mono.empty());

        Mono<Void> result = birdController.deleteBird("NonExistentBird");

        StepVerifier.create(result)
            .verifyComplete();
    }
}