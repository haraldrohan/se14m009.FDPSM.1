package at.technikum.se14m009.movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import at.technikum.se14m009.generated.DaoMaster;
import at.technikum.se14m009.generated.DaoSession;
import at.technikum.se14m009.generated.MovieEntity;
import at.technikum.se14m009.generated.MovieEntityDao;
import at.technikum.se14m009.generated.SearchEntity;
import at.technikum.se14m009.generated.SearchEntityDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Holds Database specific operations.
 */
public class MovieDatabase {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private final MovieEntityDao movieEntityDao;
    private final SearchEntityDao searchEntityDao;
    private final MovieService movieService;

    /**
     * Creates a database instance
     * @param context for the calling ui element
     * @param movieService to be used for loading data
     */
    public MovieDatabase(Context context, MovieService movieService) {
        this.movieService = movieService;
        SQLiteDatabase db = new DaoMaster
                .DevOpenHelper(context, "movies-db", null)
                .getWritableDatabase();
        //DaoMaster.dropAllTables(db, true);
        //DaoMaster.createAllTables(db, false);
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        movieEntityDao = daoSession.getMovieEntityDao();
        searchEntityDao = daoSession.getSearchEntityDao();
    }

    /**
     * Creates a database instance without service
     * @param context for the calling ui element
     */
    public MovieDatabase(Context context) {
        this(context,null);
    }

    /***
     * Get's and stores the search result including movie items
     * by given search param.
     * @param searchParam
     * @return Search result including all found movie items.
     */
    public SearchResult searchMovies(String searchParam) {

        QueryBuilder<SearchEntity> searchBuilder = searchEntityDao.queryBuilder();
        searchBuilder.where(SearchEntityDao.Properties.SearchTerm.eq(searchParam));
        SearchEntity searchEntity = searchBuilder.unique();

        // get the search result if it isn't cached already
        if (searchEntity == null)
        {
            long searchId = searchEntityDao.count() + 1;
            SearchResult searchResult = movieService.searchMovies(searchParam);
            searchEntity = new SearchEntity(searchId, searchParam);
            searchEntityDao.insert(searchEntity);
            for (MovieItem i:searchResult.Movies) {
                movieEntityDao.insert(new MovieEntity
                        (i.Title, i.Runtime, i.Year, i.Poster, i.imdbID, searchId));
            }
        }

        // build the search result from cache ...
        SearchResult searchResult = new SearchResult();
        searchResult.Movies = new ArrayList<MovieItem>();

        QueryBuilder<MovieEntity> movieBuilder = movieEntityDao.queryBuilder();
        movieBuilder.where(MovieEntityDao.Properties.SearchId.eq(searchEntity.getId()));

        // ... including movies
        for (MovieEntity movieEntity : movieBuilder.list()) {
            MovieItem movieItem = new MovieItem();
            movieItem.Title = movieEntity.getTitle();
            movieItem.Runtime = movieEntity.getRuntime();
            movieItem.Year = movieEntity.getYear();
            movieItem.Poster = movieEntity.getPoster();
            movieItem.imdbID = movieEntity.getImdbId();
            searchResult.Movies.add(movieItem);
        }

        return searchResult;
    }

    /**
     * Returns the stored movie item by given imdbId.
     * @param imdbID
     * @return the stored movie item.
     */
    public MovieItem getMovie(String imdbID) {

        // get the individual movie item from cache
        QueryBuilder<MovieEntity> movieBuilder = movieEntityDao.queryBuilder();
        movieBuilder.where(MovieEntityDao.Properties.ImdbId.eq(imdbID));
        MovieEntity movieEntity = movieBuilder.uniqueOrThrow();

        MovieItem movieItem = new MovieItem();
        movieItem.Title = movieEntity.getTitle();
        movieItem.Runtime = movieEntity.getRuntime();
        movieItem.Year = movieEntity.getYear();
        movieItem.Poster = movieEntity.getPoster();
        movieItem.imdbID = movieEntity.getImdbId();
        return movieItem;
    }

    /**
     * @return all stored search results.
     */
    public List<SearchResult> CachedResults() {
        List<SearchResult> searchResults = new ArrayList<>();
        for (SearchEntity searchEntity : searchEntityDao.loadAll()) {
            SearchResult searchResult = new SearchResult();
            searchResult.SearchTerm = searchEntity.getSearchTerm();
            searchResults.add(searchResult);
        }
        return searchResults;
    }
}