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

    @Click
    public void search(View view) {
        // get the search term provided by the user
        String message = search_text.getText().toString();

        // create the movie list fragment and forward the search term
        MovieListFragment newFragment = new MovieListFragment_().builder().SearchParam(message).build();

        // change the fragment from search to movie list
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, newFragment).addToBackStack(null).commit();
    }
}
