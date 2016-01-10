package aboali.mostafa.moviesapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import aboali.mostafa.moviesapp.asynctasks.FetchReviews;
import aboali.mostafa.moviesapp.asynctasks.FetchTrails;
import aboali.mostafa.moviesapp.model.Reviews;


public class DetailsActivity extends AppCompatActivity {

    public String itemID;
    ListView lvDetails;
    FetchReviews reviewsTask;
    FetchTrails trailsTask;
    Reviews r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //-------------//
        r=new Reviews();
        //-------------------//
        lvDetails=(ListView)findViewById(R.id.lvDetails);
        //-------------------  id //
        itemID=MainActivity.resultFilms.getId()+"";
        //==================== call async =================//
        reviewsTask=new FetchReviews();
        reviewsTask.execute(itemID);
        trailsTask=new FetchTrails(lvDetails,this);
        trailsTask.execute(itemID);
        //---------------------------

    }


}
