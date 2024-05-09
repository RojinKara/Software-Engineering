package com.flickfinder.model;

public class MovieRating extends Movie {

    private double rating;
    private int votes;
    /**
     * Constructs a Movie Rating object with the specified id, title, year, rating and votes.
     *
     * @param id    the unique identifier of the movie
     * @param title the title of the movie
     * @param year  the release year of the movie
     * @param rating the rating of the movie
     * @param votes the number of votes for the movie
     */
    public MovieRating(int id, String title, int year, double rating, int votes) {
        super(id, title, year);
        this.rating = rating;
        this.votes = votes;
    }

    /**
     * Returns the rating of the movie.
     *
     * @return the rating of the movie
     */

    public double getRating() {
        return this.rating;
    }

    /**
     * Returns the number of votes for the movie.
     *
     * @return the number of votes for the movie
     */

    public int getVotes() {
        return this.votes;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating the rating to set
     */

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Sets the number of votes for the movie.
     *
     * @param votes the number of votes to set
     */

    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Returns a string representation of the movie rating.
     * This is primarily used for debugging purposes.
     *
     * @return a string representation of the movie rating
     */

    @Override
    public String toString() {
        return "Movie Rating [id=" + super.getId() + ", title=" + super.getTitle()
                + ", year=" + super.getYear() + ", rating=" + this.rating + ", votes=" + this.votes + "]";
    }

}
