package com.project.birdSightings.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "sightings")
public class Sighting {
    @Id
    private String id;
    @NonNull
    private Bird bird;
    @NonNull
    private String location;
    @NonNull
    private LocalDate date;
}
