package at.technikum.se14m009.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MovieItem {
    public String Title;
    public String Runtime;
    public String Year;
    public String Poster;
    public String imdbID;

    @Override
    public String toString() {
        return Title;
    }
}
