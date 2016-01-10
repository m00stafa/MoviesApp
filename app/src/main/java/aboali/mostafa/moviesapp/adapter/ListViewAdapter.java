package aboali.mostafa.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import aboali.mostafa.moviesapp.MainActivity;
import aboali.mostafa.moviesapp.R;
import aboali.mostafa.moviesapp.model.ResultFilms;
import aboali.mostafa.moviesapp.model.Reviews;
import aboali.mostafa.moviesapp.model.Videos;
import aboali.mostafa.moviesapp.fragment.FragmentGridView;
import aboali.mostafa.moviesapp.sql_data.DbMethods;

/**
 * Created by Mostafa on 06/01/2016.
 */
public class ListViewAdapter extends BaseAdapter {

    Videos video;
    Reviews review;
    Context context;
    ResultFilms film;
    LayoutInflater inflater;
    DbMethods db;
    String url;

    //-
    int flag = 0;


    public ListViewAdapter(Videos video,Context context, Reviews review){
        this.video=video;
        this.context=context;
        this.review=review;
        this.film= FragmentGridView.resultFilms;
        db=new DbMethods(context);
    }


    @Override
    public int getCount() {
            return 1+review.getResults().size()+video.getResults().size();
    }

    @Override
    public Object getItem(int position) {
        if(getType(position)=="header"){
            return film;
        }else if (getType(position)=="trailer"){
            return video.getResults().get(position-1);
        }else {
            return review.getResults().get(position-1-video.getResults().size());
        }
    }


    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if(getType(position)=="header"){
                //-- header
                view = inflater.inflate(R.layout.list_header, null);
            }else if (getType(position)=="trailer"){
                //-- trail
                view = inflater.inflate(R.layout.list_item_trails, null);
            }else{
                //-- review
                view = inflater.inflate(R.layout.list_item_reviews, null);
            }


        if(getType(position)=="header"){
            //-- header

            TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
            TextView tvDate= (TextView) view.findViewById(R.id.tvDate);
            TextView tvVote= (TextView) view.findViewById(R.id.tvVote);
            TextView tvOverview= (TextView) view.findViewById(R.id.tvOverview);
            ImageView imPoster= (ImageView) view.findViewById(R.id.imPoster);
            final ImageView imFavorit= (ImageView) view.findViewById(R.id.imFavorit);

            //============================

          //  boolean b=true;

            if(db.getIds().contains(MainActivity.resultFilms.getId())){
                imFavorit.setImageResource(android.R.drawable.star_big_on);
                flag=1;
            }else{
                imFavorit.setImageResource(android.R.drawable.star_big_off);
                flag=0;
            }

            imFavorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (flag ==0)
                    {
                        imFavorit.setImageResource(android.R.drawable.star_big_on);
                        flag =1;
                        db.addFavorit(MainActivity.resultFilms);
                    }
                    else
                    {
                        imFavorit.setImageResource(android.R.drawable.star_big_off);
                        flag=0;
                        db.removeFavorit(MainActivity.resultFilms);
                    }

                }
            });
            //-----------------------//
            tvTitle.setText(MainActivity.resultFilms.getTitle());
            tvDate.setText(MainActivity.resultFilms.getReleaseDate());
            tvVote.setText(MainActivity.resultFilms.getVoteAverage()+"");
            tvOverview.setText(MainActivity.resultFilms.getOverview());
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185"+MainActivity.resultFilms.getPosterPath())
                    .into(imPoster);
        }else if (getType(position)=="trailer"){
            //-- trailer
            ImageView imtrail=(ImageView) view.findViewById(R.id.imTrailLink) ;
            url="https://www.youtube.com/watch?v="+video.getResults().get(position-1).getKey();
            imtrail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });
        }else{
            //-- review
            TextView tvAuthor= (TextView) view.findViewById(R.id.tvAuthor);
            TextView tvContent= (TextView) view.findViewById(R.id.tvContent);
            tvAuthor.setText(review.getResults().get(position-1-video.getResults().size()).getAuthor());
            tvContent.setText(review.getResults().get(position-1-video.getResults().size()).getContent());

        }

        return view;
    }

///------------------  know  typ  to draw  the  suitable  layout
    String getType(int position){
        if(position==0)
            return "header";
        else if(position<=video.getResults().size())
            return "trailer";
        else
            return "review";

    }
}
