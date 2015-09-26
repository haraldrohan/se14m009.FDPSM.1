package at.technikum.se14m009.movies;

import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

/**
 * A fragment representing a list of movies.
 */
@EFragment
public class MovieListFragment extends ListFragment {

    @FragmentArg
    String SearchParam;

    @RestService
    MovieService movieService;

    @AfterViews
    protected void init() {
        // create and assign the list adapter
        final ArrayAdapter<MovieItem> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        initMovies();
    }

    @Background
    void initMovies()
    {
        final SearchResult searchResult = movieService.searchMovies(SearchParam);
        setMovies(searchResult.Movies);
    }

    @UiThread
    void setMovies(List<MovieItem> movies) {
        ArrayAdapter<MovieItem> adapter = (ArrayAdapter<MovieItem>)getListAdapter();
        adapter.addAll(movies);
    }

    @ItemClick
    void listItemClicked(MovieItem movieItem)
    {
        // one individual movie has been selected
        // create a movie detail fragment and forward the movie imdbID
        MovieDetailFragment newFragment = new MovieDetailFragment_().builder().ImdbID(movieItem.imdbID).build();

        // change the fragment from movie list to movie details
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, newFragment).addToBackStack(null).commit();
    }
}
