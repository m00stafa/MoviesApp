package aboali.mostafa.moviesapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import aboali.mostafa.moviesapp.DeveloperKeys;
import aboali.mostafa.moviesapp.adapter.ListViewAdapter;
import aboali.mostafa.moviesapp.model.Reviews;
import aboali.mostafa.moviesapp.model.Videos;

/**
 * Created by Mostafa on 09/01/2016.
 */
public class FetchTrails extends AsyncTask<String, Void, Videos> {

  //  public String LOG_TAG="FetchTrials";
    ListView lvDetails;
    Context context;
    static   Reviews r;

    public FetchTrails(ListView lvDetails,Context context){
        this.lvDetails=lvDetails;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(final Videos videos) {
        super.onPostExecute(videos);
        // set adapter
        lvDetails.setAdapter(new ListViewAdapter(videos,context,r));
    }

    @Override
    protected Videos doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String trailsJsonStr = null;

        try {
            //  the URL for the moviedb
            String baseUrl = "http://api.themoviedb.org/3/movie/"+params[0]+"/videos?api_key="+ DeveloperKeys.APP_KEY;
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
            trailsJsonStr = buffer.toString();
            // Log.v(LOG_TAG,  trailsJsonStr);
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
        String jsonOutput = trailsJsonStr;
        Videos videos = gson.fromJson(jsonOutput, Videos.class);
     //   Log.v(LOG_TAG," size : "+ videos.getResults().size());

        return videos;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}