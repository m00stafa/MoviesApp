package aboali.mostafa.moviesapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import aboali.mostafa.moviesapp.adapter.GridViewAdapter;
import aboali.mostafa.moviesapp.model.Film;

/**
 * Created by Mostafa on 08/01/2016.
 */
public class FetchFilmData extends AsyncTask<String, Void, Film> {

   // public final String  LOG_TAG="FetchFilmData";
    public static Film film; // shared object
    GridView gridView;
    Context context;

    public FetchFilmData(GridView gridView,Context context){
        this.gridView=gridView;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(final Film film) {
        super.onPostExecute(film);
        // set adapter
        this.film=film;
        gridView.setAdapter(new GridViewAdapter(film,context));
    }

    @Override
    protected Film doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;

        try {
            //  the URL for the moviedb
            String baseUrl = params[0];
            URL url = new URL(baseUrl);

            // Create the request  to open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
           // Log.v(LOG_TAG,  movieJsonStr);
        } catch (IOException e) {
           // Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                 //   Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        Gson gson = new Gson();
        String jsonOutput = movieJsonStr;
        Film films = gson.fromJson(jsonOutput, Film.class);
//        Log.v(LOG_TAG," size : "+ films.getResultFilmses().size());

        return films;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}