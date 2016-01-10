package aboali.mostafa.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import aboali.mostafa.moviesapp.R;
import aboali.mostafa.moviesapp.model.Film;

/**
 * Created by Mostafa on 29/12/2015.
 */
public class GridViewAdapter extends BaseAdapter{

    Film film;
    Context context;
    LayoutInflater inflater;

    public GridViewAdapter(Film film,Context context){
        this.film=film;
        this.context=context;
    }

    @Override
    public int getCount() {
        return film.getResultFilmses().size();
    }

    @Override
    public Object getItem(int position) {
        return film.getResultFilmses().get(position);
    }

    @Override
    public long getItemId(int position) {
        return film.getResultFilmses().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_grid_item, null);
        } else {
            view = convertView;
        }

        ImageView imPoster=(ImageView) view.findViewById(R.id.imageView);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185"+film.getResultFilmses().get(position).getPosterPath())
                .into(imPoster);

        return view;
    }
}
