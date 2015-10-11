package at.technikum.se14m009.movies;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_movie)
@OptionsMenu(R.menu.menu_movie)
public class MovieActivity extends AppCompatActivity
{
    @ViewById(R.id.fragment)
    View fragment;

    @AfterViews
    protected void init() {

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (fragment != null) {

            // Create a new Fragment to be placed in the activity layout
            MovieSearchFragment firstFragment = new MovieSearchFragment_().builder().build();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, firstFragment).commit();
        }
    }
}
