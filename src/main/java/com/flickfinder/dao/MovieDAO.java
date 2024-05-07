package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * The Data Access Object for the Movie table.
 *
 * This class is responsible for getting data from the Movies table in the
 * database.
 *
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
	 */
	public MovieDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	public List<Movie> getAllMovies(int limit) throws SQLException {
		List<Movie> movies = new ArrayList<>();

		String statement = "select * from movies limit ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, limit);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public Movie getMovieById(int id) throws SQLException {

		String statement = "select * from movies where id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}
		
		// return null if the id does not return a movie.

		return null;

	}

	/**
	 * Returns a list of people by a specified movie id.
	 *
	 * @param id the id of the movie
	 * @return a list of people by a specified movie id
	 * @throws SQLException if a database error occurs
	 */

	public List<Person> getStarsByMovie(int id) throws SQLException {
		List<Person> people = new ArrayList<>();

		String statement = "select * from people, stars where stars.movie_id = ? and stars.person_id = people.id";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();


		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
		}

		return people;
	}

	/**
	 * Returns a list of movies ordered by their ratings in descending order for a given year.
	 *
	 * @param year the year of the movie(s)
	 * @param votes the minimum number of votes
	 * @param limit the number of movies to return
	 * @return a list of movies by rating order
	 * @throws SQLException if a database error occurs
	 */

	public List<MovieRating> getMoviesByRatingOrder(int year, int votes, int limit) throws SQLException {
		List<MovieRating> movieRatings = new ArrayList<>();

		String statement = "select * from movies, ratings where ratings.movie_id = movies.id" +
				" and movies.year = ? and ratings.votes > ? order by ratings.rating desc limit ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, votes);
		ps.setInt(3, limit);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			movieRatings.add(new MovieRating(rs.getInt("id"), rs.getString("title"),
					rs.getInt("year"), rs.getDouble("rating"), rs.getInt("votes")));
		}

		return movieRatings;
	}

}
