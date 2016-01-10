package aboali.mostafa.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import aboali.mostafa.moviesapp.adapter.FavoritsAdapter;
import aboali.mostafa.moviesapp.sql_data.*;

public class FavoritsActivity extends AppCompatActivity {

    GridView gridFavorit;
    DbMethods db; // object db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorits);
        //---------------//
        db=new DbMethods(this);
        //-----------------//
        gridFavorit = (GridView) findViewById(R.id.gridfavorit);
        gridFavorit.setAdapter(new FavoritsAdapter(db.getTitels(),db.getPosters(),this));
    }
}
