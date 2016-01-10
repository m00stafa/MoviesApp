package aboali.mostafa.moviesapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import aboali.mostafa.moviesapp.DeveloperKeys;
import aboali.mostafa.moviesapp.R;
import aboali.mostafa.moviesapp.asynctasks.FetchFilmData;
import aboali.mostafa.moviesapp.model.ResultFilms;

/**
 * Created by Mostafa on 08/01/2016.
 */
public class FragmentGridView extends Fragment {

    GridView gridView;
    FetchFilmData films;
    public static ResultFilms resultFilms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        //----------------------------//

        gridView = (GridView) rootView.findViewById(R.id.gridview);
        //-------------//
        films = new FetchFilmData(gridView,getActivity().getApplicationContext());
        films.execute("http://api.themoviedb.org/3/discover/movie?api_key="+ DeveloperKeys.APP_KEY);
        //-------------------------------//
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        //----------------//
        resultFilms = FetchFilmData.film.getResultFilmses().get(position);
        //------------//
          }
         });

        //-----------------------------//
        return rootView;
    }



}
