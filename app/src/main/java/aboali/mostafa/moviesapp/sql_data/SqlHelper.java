package aboali.mostafa.moviesapp.sql_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	final static String db_name = "movieSchema";
	final static int db_version = 2;
	
	final static String movies = "movies";
	final static String id = "id";
	final static String original_title = "original_title";
	final static String poster_path = "poster_path";
	final static String overview = "overview";
	final static String release_date = "release_date";
	final static String create_movies = " create table " + movies + "(" + id
			+ " integer primary key  , " + original_title
			+ " varchar(50) , " + poster_path + " varchar(50)," + overview
			+ " varchar(50) , " + release_date + " varchar(50)  );";


	public SqlHelper(Context context) {
		super(context, db_name, null, db_version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(create_movies);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("drop table if exists movies");
		onCreate(db);

	}

}
