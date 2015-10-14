package at.technikum.se14m009.movies;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A fragment to search for movies.
 */
@EFragment(R.layout.fragment_search)
public class MovieSearchFragment extends Fragment {

    @ViewById
    EditText search_text;

    /**
     *
     * @param view search result based on search term
     */
    @Click
    public void search(View view) {
        // get the search term provided by the user
        String searchText = search_text.getText().toString();

        // create the movie list fragment and forward the search term
        MovieListFragment listFragment =
                new MovieListFragment_().builder().SearchParam(searchText).build();

        // change the fragment from search to movie list
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, listFragment).addToBackStack(null).commit();
    }

    /**
     *
     * @param view search results based on cached items
     */
    @Click
    public void cache(View view) {

        // create the movie list fragment and forward the search term
        CacheListFragment listFragment = new CacheListFragment_();

        // change the fragment from search to movie list
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, listFragment).addToBackStack(null).commit();
    }
}
