package at.technikum.se14m009.movies;

import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * A fragment representing a list of cached results.
 */
@EFragment
public class CacheListFragment extends ListFragment {

    private MovieDatabase db;

    @AfterViews
    protected void init() {
        db = new MovieDatabase(getContext());
        // create and assign the list adapter
        final ArrayAdapter<SearchResult> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        initResults();
    }

    /**
     * Loads all stored search results.
     */
    @Background
    void initResults() {
        final List<SearchResult> cachedResults = db.CachedResults();
        setResult(cachedResults);
    }

    /**
     * @param results list of search terms to be shown
     */
    @UiThread
    void setResult(List<SearchResult> results) {
        ArrayAdapter<SearchResult> adapter = (ArrayAdapter<SearchResult>)getListAdapter();
        adapter.addAll(results);
    }

    /**
     * Search result has been selected
     * @param searchResult contains search term to be used
     */
    @ItemClick
    void listItemClicked(SearchResult searchResult)
    {
        // create the movie list fragment and forward the search term
        MovieListFragment newFragment = new MovieListFragment_().builder()
                .SearchParam(searchResult.SearchTerm).build();

        // change the fragment from search to movie list
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, newFragment).addToBackStack(null).commit();

    }
}
