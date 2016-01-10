package aboali.mostafa.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import aboali.mostafa.moviesapp.asynctasks.FetchFilmData;
import aboali.mostafa.moviesapp.asynctasks.FetchReviews;
import aboali.mostafa.moviesapp.asynctasks.FetchTrails;
import aboali.mostafa.moviesapp.model.ResultFilms;


public class MainActivity extends AppCompatActivity {

    public static ResultFilms resultFilms;  // shared  value to get  the  item click  in grid view
    GridView gridView;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv= (ListView) findViewById(R.id.lvDetails);
        b=(Button) findViewById(R.id.showFavorits) ;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoritsActivity.class);
                startActivity(intent);
            }
        });


        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //----------------//
                resultFilms = FetchFilmData.film.getResultFilmses().get(position);
                //------------//
                if(lv==null) {
                    // phone
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    startActivity(intent);
                }else{
                    // tablet
                    FetchReviews reviewsTask=new FetchReviews();
                    reviewsTask.execute(""+resultFilms.getId());
                    FetchTrails trailsTask=new FetchTrails(lv,getApplicationContext());
                    trailsTask.execute(""+resultFilms.getId());
                }

            }
        });

      Spinner  sp=(Spinner) findViewById(R.id.spOrderMethod);
        ArrayAdapter adapter=new ArrayAdapter(this
                ,android.R.layout.simple_expandable_list_item_1,new String[]{"Most Popular","Highest Rated"});
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    FetchFilmData films = new FetchFilmData(gridView,getApplicationContext());
                    films.execute("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+DeveloperKeys.APP_KEY);
                }else if(position==1) {
                    FetchFilmData films = new FetchFilmData(gridView, getApplicationContext());
                    films.execute("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key="+DeveloperKeys.APP_KEY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
