package at.technikum.se14m009.movies;

import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;

/**
 * A fragment representing a list of movies.
 */
@EFragment
public class MovieListFragment extends ListFragment {

    @FragmentArg
    String SearchParam;

    @AfterViews
    protected void init() {
        // create and assign the list adapter
        final ArrayAdapter<MovieService.MovieItem> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1);
        setListAdapter(adapter);
        // fill the adapter items in another worker thread
        MovieService.SetItems(getContext(), adapter, SearchParam);
    }

    @ItemClick
    void listItemClicked(MovieService.MovieItem movieItem)
    {
        // one individual movie has been selected
        // create a movie detail fragment and forward the movie imdbID
        MovieDetailFragment newFragment = new MovieDetailFragment_().builder().ImdbID(movieItem.imdbID).build();

        // change the fragment from movie list to movie details
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, newFragment).addToBackStack(null).commit();
    }
}
