package com.flickfinder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieRatingTest {

    /**
     * The movie rating object to be tested.
     */
    private MovieRating movieRating;

    /**
     * Set up the movie rating object before each test.
     *
     */
    @BeforeEach
    public void setUp() {
        movieRating = new MovieRating(1, "The Matrix", 1999, 8.7, 1000);
    }

    /**
     * Test the movie object is created with the correct values.
     */
    @Test
    public void testMovieRatingCreated() {
        assertEquals(1, movieRating.getId());
        assertEquals("The Matrix", movieRating.getTitle());
        assertEquals(1999, movieRating.getYear());
        assertEquals(8.7, movieRating.getRating());
        assertEquals(1000, movieRating.getVotes());
    }

    /**
     * Test the movie rating object is created with the correct values.
     */
    @Test
    public void testMovieRatingSetters() {
        movieRating.setId(2);
        movieRating.setTitle("The Matrix Reloaded");
        movieRating.setYear(2003);
        movieRating.setRating(7.2);
        movieRating.setVotes(2000);
        assertEquals(2, movieRating.getId());
        assertEquals("The Matrix Reloaded", movieRating.getTitle());
        assertEquals(2003, movieRating.getYear());
        assertEquals(7.2, movieRating.getRating());
        assertEquals(2000, movieRating.getVotes());
    }
}
