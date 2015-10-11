package at.technikum.se14m009.movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import at.technikum.se14m009.generated.DaoMaster;
import at.technikum.se14m009.generated.DaoSession;
import at.technikum.se14m009.generated.MovieItemDao;
import at.technikum.se14m009.generated.SearchResultDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Holds Database specific operations.
 */
public class MovieDatabase {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private final MovieItemDao movieItemDao;
    private final SearchResultDao searchResultDao;
    private final MovieService movieService;

    public MovieDatabase(Context context, MovieService movieService) {
        this.movieService = movieService;
        SQLiteDatabase db = new DaoMaster
                .DevOpenHelper(context, "movies-db", null)
                .getWritableDatabase();
        //DaoMaster.dropAllTables(db, true);
        //DaoMaster.createAllTables(db, false);
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        movieItemDao = daoSession.getMovieItemDao();
        searchResultDao = daoSession.getSearchResultDao();
    }

    public SearchResult searchMovies(String searchParam) {

        QueryBuilder<at.technikum.se14m009.generated.SearchResult> builder = searchResultDao.queryBuilder();
        builder.where(SearchResultDao.Properties.SearchTerm.eq(searchParam));
        at.technikum.se14m009.generated.SearchResult searchResult = builder.unique();

        if (searchResult == null)
        {
            SearchResult r = movieService.searchMovies(searchParam);
            searchResult = new at.technikum.se14m009.generated.SearchResult(searchParam);
            searchResultDao.insert(searchResult);
        }

        SearchResult r = movieService.searchMovies(searchParam);
        //searchResultDao.insert(new at.technikum.se14m009.generated.SearchResult(searchParam));
        return r;
    }

    public MovieItem getMovie(String imdbID) {

        QueryBuilder<at.technikum.se14m009.generated.MovieItem> builder = movieItemDao.queryBuilder();
        builder.where(MovieItemDao.Properties.ImdbID.eq(imdbID));
        at.technikum.se14m009.generated.MovieItem movieItem = builder.unique();

        if (movieItem == null)
        {
            MovieItem i = movieService.getMovie(imdbID);
            movieItem = new at.technikum.se14m009.generated.MovieItem(
                    i.Title,i.Runtime,i.Year,i.Poster,i.imdbID);
            movieItemDao.insert(movieItem);
        }

        MovieItem i = new MovieItem();
        i.Title = movieItem.getTitle();
        i.Runtime = movieItem.getRuntime();
        i.Year = movieItem.getYear();
        i.Poster = movieItem.getPoster();
        i.imdbID = movieItem.getImdbID();
        return i;
    }
}