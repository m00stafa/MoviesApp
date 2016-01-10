package aboali.mostafa.moviesapp.adapter;

/**
 * Created by Mostafa on 09/01/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import aboali.mostafa.moviesapp.R;
import android.widget.TextView;
import java.util.ArrayList;



/**
 * Created by Mostafa on 29/12/2015.
 */
public class FavoritsAdapter extends BaseAdapter {

    ArrayList<String> titles;
    ArrayList<String> posters;
    Context context;
    LayoutInflater inflater;

    public FavoritsAdapter( ArrayList<String> titles, ArrayList<String> posters,Context context){
        this.titles=titles;
        this.posters=posters;
        this.context=context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_grid_item_favorits, null);
        } else {
            view = convertView;
        }

        ImageView imPoster=(ImageView) view.findViewById(R.id.imageFavorit);
        TextView imTitle=(TextView) view.findViewById(R.id.textTitel);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185"+posters.get(position))
                .into(imPoster);
        imTitle.setText(titles.get(position));

        return view;
    }
}
