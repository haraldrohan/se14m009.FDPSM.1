package at.technikum.se14m009.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Handles calls to the movie service.
 */
public class MovieService {

    private static RequestQueue queue;
    private static RequestQueue getQueue(final Context context)
    {
        if (queue == null)
            queue = Volley.newRequestQueue(context);
        return queue;
    }

    public static void SetItems(final Context context, final ArrayAdapter<MovieItem> adapter, String search) {

        try
        {
            String query = URLEncoder.encode(search, "utf-8");
            String url = "http://www.omdbapi.com/?s="+query;

            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("Search");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final String title = jsonObject.getString("Title");
                                    final String imdbID = jsonObject.getString("imdbID");
                                    adapter.add(new MovieItem(Integer.toString(i), imdbID , title));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ShowError(context, e.getLocalizedMessage());
                            }
                            //mTxtDisplay.setText("Response: " + response.toString());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ShowError(context, error.getLocalizedMessage());
                        }
                    });
            getQueue(context).add(jsObjRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ShowError(context, e.getLocalizedMessage());
        }
    }

    public static void SetItem(final Context context, final View view, String imdbID) {

        String url = "http://www.omdbapi.com/?i=" + imdbID;
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                                TextView title = (TextView) view.findViewById(R.id.movieTitle);
                                title.setText(response.getString("Title"));
                                TextView runtime = (TextView) view.findViewById(R.id.movieRuntime);
                                runtime.setText(response.getString("Runtime"));
                                TextView year = (TextView) view.findViewById(R.id.movieYear);
                                year.setText(response.getString("Year"));
                                ImageView poster = (ImageView) view.findViewById(R.id.moviePoster);
                                SetImage(context,poster, response.getString("Poster"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowError(context, e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ShowError(context, error.getLocalizedMessage());
                    }
                });
        getQueue(context).add(jsObjRequest);
    }

    private static void SetImage(final Context context, final ImageView mImageView, String url)
    {
        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        ShowError(context, error.getLocalizedMessage());
                    }
                });

        getQueue(context).add(request);
    }

    private static void ShowError(Context context, String errorText)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Error");

        // Setting Dialog Message
        alertDialog.setMessage(errorText);

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * A movie item representing a piece of content.
     */
    public static class MovieItem {
        public String id;
        public String imdbID;
        public String content;

        public MovieItem(String id, String imdbID, String content) {
            this.id = id;
            this.imdbID = imdbID;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}


/*
http://www.omdbapi.com/?i=tt0133093
{"Title":"The Matrix","Year":"1999","Rated":"R","Released":"31 Mar 1999","Runtime":"136 min","Genre":"Action, Sci-Fi",
"Director":"Andy Wachowski, Lana Wachowski","Writer":"Andy Wachowski, Lana Wachowski",
"Actors":"Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving",
"Plot":"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
"Language":"English","Country":"USA, Australia","Awards":"Won 4 Oscars. Another 33 wins & 40 nominations.",
"Poster":"http://ia.media-imdb.com/images/M/MV5BMTkxNDYxOTA4M15BMl5BanBnXkFtZTgwNTk0NzQxMTE@._V1_SX300.jpg","Metascore":"73","imdbRating":"8.7","imdbVotes":"1,093,816","imdbID":"tt0133093","Type":"movie","Response":"True"}
 */