package com.flickfinder.controller;

import java.sql.SQLException;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.model.Movie;

import io.javalin.http.Context;

/**
 * The controller for the movie endpoints.
 * 
 * The controller acts as an intermediary between the HTTP routes and the DAO.
 * 
 * As you can see each method in the controller class is responsible for
 * handling a specific HTTP request.
 * 
 * Methods a Javalin Context object as a parameter and uses it to send a
 * response back to the client.
 * We also handle business logic in the controller, such as validating input and
 * handling errors.
 *
 * Notice that the methods don't return anything. Instead, they use the Javalin
 * Context object to send a response back to the client.
 */

public class MovieController {

	/**
	 * The movie data access object.
	 */

	private final MovieDAO movieDAO;

	/**
	 * Constructs a MovieController object and initializes the movieDAO.
	 */
	public MovieController(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Returns a list of all movies in the database. (limited to 50)
	 * The limit parameter is used to limit the number of movies returned.
	 * If a database error occurs, a 500 status code is returned.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getAllMovies(Context ctx) {
		try {
			int limit = 50;
			if (ctx.queryParam("limit") != null && !(Integer.parseInt(ctx.queryParam("limit")) <= 0)) {
				limit = Integer.parseInt(ctx.queryParam("limit"));
			}
			ctx.json(movieDAO.getAllMovies(limit));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the movie with the specified id.
	 * If the movie is not found, a 404 status code is returned.
	 * If a database error occurs, a 500 status code is returned.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));

			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(movieDAO.getMovieById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a list of people by a specified movie id.
	 * If a database error occurs, a 500 status code is returned.
	 *
	 * @param ctx the Javalin context
	 */
	public void getPeopleByMovieId(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));

			ctx.json(movieDAO.getStarsByMovie(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a list of movie ratings which were released in the
	 * specified year and have votes greater than the specified value.
	 *
	 * The limit parameter is used to limit the number of movies returned.
	 * If a database error occurs, a 500 status code is returned.
	 *
	 * @param ctx the Javalin context
	 */
	public void getRatingsByYear(Context ctx) {
		try {
			int year = Integer.parseInt(ctx.pathParam("year"));

			int limit = 50;
			if (ctx.queryParam("limit") != null && !(Integer.parseInt(ctx.queryParam("limit")) <= 0)) {
				limit = Integer.parseInt(ctx.queryParam("limit"));
			}
			int votes = 1000;
			if (ctx.queryParam("votes") != null && !(Integer.parseInt(ctx.queryParam("votes")) <= 0)) {
				votes = Integer.parseInt(ctx.queryParam("votes"));
			}
			ctx.json(movieDAO.getMoviesByRatingOrder(year,votes, limit));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}
}
