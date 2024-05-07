package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Person;
import io.javalin.http.Context;

import java.sql.SQLException;

public class PersonController {
    private final PersonDAO personDAO;

    /**
     * Constructs a PersonController object and initializes the personDAO.
     */
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Returns a list of all people in the database.
     * The limit parameter is used to limit the number of people returned.
     * If a database error occurs, a 500 status code is returned.
     *
     * @param ctx the Javalin context
     */
    public void getAllPeople(Context ctx) {
        try {
            int limit = 50;
            if (ctx.queryParam("limit") != null && !(Integer.parseInt(ctx.queryParam("limit")) <= 0)) {
                limit = Integer.parseInt(ctx.queryParam("limit"));
            }
            ctx.json(personDAO.getAllPeople(limit));
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }

    /**
     * Returns the person with the specified id.
     * If the person is not found, a 404 status code is returned.
     * If a database error occurs, a 500 status code is returned.
     *
     * @param ctx the Javalin context
     */
    public void getPersonById(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            Person person = personDAO.getPersonById(id);
            if (person == null) {
                ctx.status(404);
                ctx.result("Person not found");
                return;
            }
            ctx.json(personDAO.getPersonById(id));
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of movies by a specified person id.
     * If a database error occurs, a 500 status code is returned.
     *
     * @param ctx the Javalin context
     */

    public void getMoviesStarringPerson(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            ctx.json(personDAO.getMoviesByStar(id));
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }
}