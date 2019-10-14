package ua.ho.webdiz.flickr;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FlickrUrl {

    private static final String TAG = "FlickrUrl";
    private static final String BASE_URL = "https://www.flickr.com/services/rest/";

    private static final String PARAM_METHOD = "method";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_CALLBACK = "nojsoncallback";
    private static final String PARAM_EXTRAS = "extras";

    private static final String METHOD = "flickr.photos.getRecent";
    private static final String API_KEY = "90bdc5f674e56f4c9d12c2ea49e8899e";
    private static final String FORMAT = "json";
    private static final String CALLBACK = "1";
    private static final String EXTRAS_S = "url_s,url_c";

    public static URL buildURL() {
        URL url = null;

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD, METHOD)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_FORMAT, FORMAT)
                .appendQueryParameter(PARAM_CALLBACK, CALLBACK)
                .appendQueryParameter(PARAM_EXTRAS, EXTRAS_S)
                .build();

        try {
            url = new URL(uri.toString());
            Log.d("MyTag", uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static JSONObject getFlickrJSONObject() {
        URL url = FlickrUrl.buildURL();
        JSONObject result = null;
        try {
            result = new JSONLoaderTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class JSONLoaderTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {

            JSONObject jsonObject = null;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                StringBuilder builder = new StringBuilder();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }

                jsonObject = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }
}
