package at.technikum.se14m009.movies;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MovieContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<MovieItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, MovieItem> ITEM_MAP = new HashMap<>();

    static {
        // Add 3 sample items.
        addItem(new MovieItem("1", "1", "Item 1"));
        addItem(new MovieItem("2", "1", "Item 2"));
        addItem(new MovieItem("3", "1", "Item 3"));
    }

    private static void addItem(MovieItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void SetItems(Context context, final ArrayAdapter<MovieItem> adapter, String mParam1) {

        String url = "http://www.omdbapi.com/?s="+mParam1;
        RequestQueue queue = Volley.newRequestQueue(context);
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
                        }
                        //mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);
    }

    /**
     * A dummy item representing a piece of content.
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
{"Title":"The Matrix","Year":"1999","Rated":"R","Released":"31 Mar 1999","Runtime":"136 min","Genre":"Action, Sci-Fi","Director":"Andy Wachowski, Lana Wachowski","Writer":"Andy Wachowski, Lana Wachowski","Actors":"Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving","Plot":"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.","Language":"English","Country":"USA, Australia","Awards":"Won 4 Oscars. Another 33 wins & 40 nominations.","Poster":"http://ia.media-imdb.com/images/M/MV5BMTkxNDYxOTA4M15BMl5BanBnXkFtZTgwNTk0NzQxMTE@._V1_SX300.jpg","Metascore":"73","imdbRating":"8.7","imdbVotes":"1,093,816","imdbID":"tt0133093","Type":"movie","Response":"True"}
 */