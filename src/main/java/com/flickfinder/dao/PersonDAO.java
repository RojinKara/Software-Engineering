package com.flickfinder.dao;


import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object for the Person table.
 *
 * This class is responsible for getting data from the People table in the
 * database.
 *
 */
public class PersonDAO {
    private final Connection connection;

    /**
     * Constructs a SQLitePersonDAO object and gets the database connection.
     *
     */
    public PersonDAO() {
        Database database = Database.getInstance();
        connection = database.getConnection();
    }

    /**
     * Returns a list of all people in the database.
     *
     * @param limit the number of people to return.
     *
     * @return a list of all people in the database
     * @throws SQLException if a database error occurs
     */

    public List<Person> getAllPeople(int limit) throws SQLException {
        List<Person> people = new ArrayList<>();

        String statement = "select * from people limit ?";
        PreparedStatement ps = connection.prepareStatement(statement);
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
        }

        return people;
    }

    /**
     * Returns the person with the specified id.
     *
     * @param id the id of the person
     * @return the person with the specified id
     * @throws SQLException if a database error occurs
     */
    public Person getPersonById(int id) throws SQLException {

        String statement = "select * from people where id = ?";
        PreparedStatement ps = connection.prepareStatement(statement);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth"));
        }

        // return null if the id does not return a person.

        return null;

    }

    /**
     * Returns a list of movies by a specified person id.
     *
     * @param id the id of the person
     * @return a list of movies by a specified person id
     * @throws SQLException if a database error occurs
     */
    public List<Movie> getMoviesByStar(int id) throws SQLException {
        List<Movie> movies = new ArrayList<>();

        String statement = "select * from movies, stars where stars.person_id = ? and stars.movie_id = movies.id";
        PreparedStatement ps = connection.prepareStatement(statement);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
        }

        return movies;
    }


}
