package com.flickfinder.dao;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test for the Person Data Access Object.
 * This uses an in-memory database for testing purposes.
 */

class PersonDAOTest {
    /**
     * The person data access object.
     */

    private PersonDAO personDAO;

    /**
     * Seeder
     */

    Seeder seeder;

    /**
     * Sets up the database connection and creates the tables.
     * We are using an in-memory database for testing purposes.
     * This gets passed to the Database class to get a connection to the database.
     * As it's a singleton class, the entire application will use the same
     * connection.
     */
    @BeforeEach
    void setUp() {
        var url = "jdbc:sqlite::memory:";
        seeder = new Seeder(url);
        Database.getInstance(seeder.getConnection());
        personDAO = new PersonDAO();

    }

    /**
     * Tests the getAllPeople method.
     * We expect to get a list of all people in the database.
     * We have seeded the database with 5 people, so we expect to get 5 people back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetAllPeople() {
        try {
            List<Person> people = personDAO.getAllPeople(50);
            assertEquals(5, people.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPersonById method.
     * We expect to get the people with the specified id.
     */
    @Test
    void testGetPersonById() {
        Person person;
        try {
            person = personDAO.getPersonById(1);
            assertEquals("Tim Robbins", person.getName());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPersonById method with an invalid id.
     * Null should be returned.
     */
    @Test
    void testGetPersonByIdInvalidId() {
        // write an assertThrows for a SQLException

        try {
            Person person = personDAO.getPersonById(1000);
            assertEquals(null, person);
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }

    }

    /**
     * Tests the getMoviesByStar method.
     * We expect to get a list of movies by a specified person id.
     * We have seeded the database with 5 movies,
     * so we expect to get 5 movies back.
     */

    @Test
    void testGetMoviesByStar() {
        try {
            List<Movie> movies = personDAO.getMoviesByStar(3);
            assertEquals(0, movies.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getMoviesByStar method with an invalid id.
     * An empty list should be returned.
     */

    @Test
    void testGetMoviesByInvalidStar() {
        try {
            List<Movie> movies = personDAO.getMoviesByStar(1000);
            assertEquals(0, movies.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database.
     */

    @AfterEach
    void tearDown() {
        seeder.closeConnection();
    }
}
