package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * Test for the Person Controller.
 */
class PersonControllerTest {
    /**
     *
     * The context object.
     */
    private Context ctx;

    /**
     * The person data access object.
     */
    private PersonDAO personDAO;

    /**
     * The person controller.
     */

    private PersonController personController;

    /**
     * Set up the test.
     */

    @BeforeEach
    void setUp() {
        // We create a mock of the PersonDAO class.
        personDAO = mock(PersonDAO.class);
        // We create a mock of the Context class.
        ctx = mock(Context.class);

        // We create an instance of the PersonController class and pass the mock object
        personController = new PersonController(personDAO);
    }

    /**
     * Tests the getAllPeople method.
     * We expect to get a list of all people in the database.
     */

    @Test
    void testGetAllPeople() {
        personController.getAllPeople(ctx);
        try {
            verify(personDAO).getAllPeople(50);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that the controller returns a 500 status code when a database error
     * occurs
     *
     * @throws SQLException if a database error occurs
     */
    @Test
    void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
        when(personDAO.getAllPeople(50)).thenThrow(new SQLException());
        personController.getAllPeople(ctx);
        verify(ctx).status(500);
    }

    /**
     * Tests the getPersonById method.
     * We expect to get the person with the specified id.
     */

    @Test
    void testGetPersonById() {
        when(ctx.pathParam("id")).thenReturn("1");
        personController.getPersonById(ctx);
        try {
            verify(personDAO).getPersonById(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test a 500 status code is returned when a database error occurs.
     *
     * @throws SQLException if a database error occurs
     */

    @Test
    void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getPersonById(1)).thenThrow(new SQLException());
        personController.getPersonById(ctx);
        verify(ctx).status(500);
    }

    /**
     * Test that the controller returns a 404 status code when a person is not found
     * or
     * database error.
     *
     * @throws SQLException if a database error occurs
     */

    @Test
    void testThrows404ExceptionWhenNoPersonFound() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getPersonById(1)).thenReturn(null);
        personController.getPersonById(ctx);
        verify(ctx).status(404);
    }

    /**
     * Tests the getMoviesStarringPerson method.
     * We expect to get a list of movies by a specified person id.
     */
    @Test
    void testGetMoviesStarringPerson() {
        when(ctx.pathParam("id")).thenReturn("1");
        personController.getMoviesStarringPerson(ctx);
        try {
            verify(personDAO).getMoviesByStar(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that the controller returns a 500 status code when a database error
     * occurs
     *
     * @throws SQLException if a database error occurs
     */

    @Test
    void testThrows500ExceptionWhenGetMoviesByStarDatabaseError() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getMoviesByStar(1)).thenThrow(new SQLException());
        personController.getMoviesStarringPerson(ctx);
        verify(ctx).status(500);
    }

}
