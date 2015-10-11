package at.technikum.se14m009.movies;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Generates rest services for movie search.
 */
@Rest(rootUrl = "http://www.omdbapi.com", converters = { MappingJackson2HttpMessageConverter.class })
public interface MovieService {
    // url variables are mapped to method parameter names.

    @Get("/?s={searchTerm}")
    SearchResult searchMovies(String searchTerm);

    @Get("/?i={imdbID}")
    MovieItem getMovie(String imdbID);
}