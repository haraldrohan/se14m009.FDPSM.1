package at.technikum.se14m009.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SearchResult {
    @JsonProperty("Search")
    public List<MovieItem> Movies;

}

/*
http://www.omdbapi.com/?i=tt0133093
{"Title":"The Matrix","Year":"1999","Rated":"R","Released":"31 Mar 1999","Runtime":"136 min","Genre":"Action, Sci-Fi",
"Director":"Andy Wachowski, Lana Wachowski","Writer":"Andy Wachowski, Lana Wachowski",
"Actors":"Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving",
"Plot":"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
"Language":"English","Country":"USA, Australia","Awards":"Won 4 Oscars. Another 33 wins & 40 nominations.",
"Poster":"http://ia.media-imdb.com/images/M/MV5BMTkxNDYxOTA4M15BMl5BanBnXkFtZTgwNTk0NzQxMTE@._V1_SX300.jpg","Metascore":"73","imdbRating":"8.7","imdbVotes":"1,093,816","imdbID":"tt0133093","Type":"movie","Response":"True"}
 */