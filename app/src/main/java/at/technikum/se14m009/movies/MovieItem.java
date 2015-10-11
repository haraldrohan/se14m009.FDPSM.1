package at.technikum.se14m009.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Holds a movie item created by json deserialization.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MovieItem {
    public String Title;
    public String Runtime;
    public String Year;
    public String Poster;
    public String imdbID;

    /**
     * @return a representation for the movie item
     */
    @Override
    public String toString() {
        return Title;
    }
}