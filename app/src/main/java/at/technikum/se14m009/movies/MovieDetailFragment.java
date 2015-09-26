package at.technikum.se14m009.movies;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EFragment(R.layout.fragment_movie)
public class MovieDetailFragment extends Fragment {

    @FragmentArg
    String ImdbID;

    @RestService
    MovieService movieService;

    @ViewById
    TextView movie_title;

    @ViewById
    TextView movie_year;

    @ViewById
    TextView movie_runtime;

    @ViewById
    ImageView movie_poster;

    @AfterViews
    protected void init() {
        initMovie();
    }

    @Background
    void initMovie()
    {
        setMovie(movieService.getMovie(ImdbID));
    }

    @UiThread
    void setMovie(MovieItem movieItem) {
        movie_title.setText(movieItem.Title);
        movie_year.setText(movieItem.Runtime);
        movie_runtime.setText(movieItem.Year);
        initImage(movieItem.Poster);
    }

    @Background
    void initImage(String url)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        setImage(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);
    }

    @UiThread
    void setImage(Bitmap bitmap) {
        movie_poster.setImageBitmap(bitmap);
    }
}
