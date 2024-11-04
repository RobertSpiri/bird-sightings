package com.project.birdSightings.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "birds")
public class Bird {
    /**
     * The unique identifier for the bird.
     * This field is annotated with @Id to indicate that it is the primary key in the MongoDB collection
     * and should not have duplicates.
     */
    @Id
    private String name;
    private String color;
    private String weight;
    private String height;
}
