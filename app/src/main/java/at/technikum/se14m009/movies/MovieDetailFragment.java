package at.technikum.se14m009.movies;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
@EFragment(R.layout.fragment_movie)
public class MovieDetailFragment extends Fragment {

    @FragmentArg
    String ImdbID;

    @AfterViews
    protected void init() {
        // use the id to fill the movie details in another worker thread
        MovieService.SetItem(getContext(), getView(), ImdbID);
    }
}
