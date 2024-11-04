package com.project.birdSightings.sightings;

import com.project.birdSightings.controller.SightingController;
import com.project.birdSightings.model.Bird;
import com.project.birdSightings.model.Sighting;
import com.project.birdSightings.service.SightingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SightingTests {

    @Mock
    private SightingService sightingService;

    @InjectMocks
    private SightingController sightingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSightingSuccessfully() {
        Bird bird = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Sighting sighting = new Sighting(bird, "Park",  LocalDate.of(2021, 7, 1));

        when(sightingService.addSighting(any(Sighting.class))).thenReturn(Mono.just(ResponseEntity.ok(sighting)));

        Mono<ResponseEntity<Sighting>> result = sightingController.addSighting(sighting);

        StepVerifier.create(result)
            .expectNext(ResponseEntity.ok(sighting))
            .verifyComplete();
    }

    @Test
    void getAllSightingsSuccessfully() {
        Bird bird1 = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Bird bird2 = new Bird("Robin", "Red", "4.5", "0.8");
        Sighting sighting1 = new Sighting(bird1, "Park", LocalDate.of(2021, 7, 1));
        Sighting sighting2 = new Sighting(bird2, "Park", LocalDate.of(2021, 8, 1));

        when(sightingService.getAllSightings()).thenReturn(Flux.just(sighting1, sighting2));

        Flux<Sighting> result = sightingController.getAllSightings();

        StepVerifier.create(result)
            .expectNext(sighting1)
            .expectNext(sighting2)
            .verifyComplete();
    }

    @Test
    void getSightingsByBirdSuccessfully() {
        Bird bird = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Sighting sighting1 = new Sighting(bird, "Park", LocalDate.of(2021, 7, 1));
        Sighting sighting2 = new Sighting(bird, "Garden", LocalDate.of(2021, 8, 1));

        when(sightingService.getSightingsByBird(bird)).thenReturn(Flux.just(sighting1, sighting2));

        Flux<Sighting> result = sightingController.getSightingsByBird(bird);

        StepVerifier.create(result)
            .expectNext(sighting1)
            .expectNext(sighting2)
            .verifyComplete();
    }

    @Test
    void getSightingByLocationSuccessfully() {
        Bird bird1 = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Bird bird2 = new Bird("Robin", "Red", "4.5", "0.8");
        Sighting sighting1 = new Sighting(bird1, "Park",   LocalDate.of(2021, 7, 1));
        Sighting sighting2 = new Sighting(bird2, "Park",  LocalDate.of(2021, 8, 1));
        when(sightingService.getSightingsByLocation("Park")).thenReturn(Flux.just(sighting1, sighting2));

        Flux<Sighting> result = sightingController.getSightingsByLocation("Park");

        StepVerifier.create(result)
            .expectNext(sighting1)
            .expectNext(sighting2)
            .verifyComplete();
    }

    @Test
    void getSightingByLocationNotFound() {
        when(sightingService.getSightingsByLocation("NonExistentLocation")).thenReturn(Flux.empty());

        Flux<Sighting> result = sightingController.getSightingsByLocation("NonExistentLocation");

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    void getSightingsByDateSuccessfully() {
        Bird bird1 = new Bird("Sparrow", "Brown", "3.5", "0.5");
        Bird bird2 = new Bird("Robin", "Red", "4.5", "0.8");
        Sighting sighting1 = new Sighting(bird1, "Park", LocalDate.of(2021, 7, 1));
        Sighting sighting2 = new Sighting(bird2, "Park", LocalDate.of(2021, 8, 1));
        LocalDate startDate = LocalDate.of(2021, 6, 1);
        LocalDate endDate = LocalDate.of(2021, 9, 1);

        when(sightingService.getSightingsByDateBetween(startDate, endDate)).thenReturn(Flux.just(sighting1, sighting2));

        Flux<Sighting> result = sightingController.getSightingsByDate(startDate, endDate);

        StepVerifier.create(result)
            .expectNext(sighting1)
            .expectNext(sighting2)
            .verifyComplete();
    }

    @Test
    void getSightingsByDateNotFound() {
        LocalDate startDate = LocalDate.of(2021, 6, 1);
        LocalDate endDate = LocalDate.of(2021, 9, 1);

        when(sightingService.getSightingsByDateBetween(startDate, endDate)).thenReturn(Flux.empty());

        Flux<Sighting> result = sightingController.getSightingsByDate(startDate, endDate);

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    void deleteSightingSuccessfully() {
        when(sightingService.deleteSighting("Sparrow")).thenReturn(Mono.empty());

        Mono<Void> result = sightingController.deleteSighting("Sparrow");

        StepVerifier.create(result)
            .verifyComplete();
    }
}