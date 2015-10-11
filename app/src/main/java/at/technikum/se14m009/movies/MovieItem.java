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
    public String getTitle() {
        return Title;
    }
    public String getYear() {
        return Year;
    }
    public String getRuntime() {
        return Runtime;
    }
    public String getPoster() {
        return Poster;
    }
    public String getImdbID() {
        return imdbID;
    }
}