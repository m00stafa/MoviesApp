package aboali.mostafa.moviesapp.sql_data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import aboali.mostafa.moviesapp.model.ResultFilms;

public class DbMethods {

	SqlHelper helper;
	SQLiteDatabase db;

	public DbMethods(Context context) {
		helper = new SqlHelper(context);
		db = helper.getWritableDatabase();
	}

	public long addFavorit(ResultFilms films) {
		long id = 0;
		ContentValues Values = new ContentValues();
		Values.put(SqlHelper.id, films.getId());
		Values.put(SqlHelper.original_title, films.getOriginalTitle());
		Values.put(SqlHelper.poster_path, films.getPosterPath());
		Values.put(SqlHelper.overview, films.getOverview());
		Values.put(SqlHelper.release_date, films.getReleaseDate());
		id = db.insert(SqlHelper.movies, null, Values);
		return id;
	}


	public ArrayList<String> getPosters() {

		ArrayList<String> poster_path = new ArrayList<>();
		String[] columns = { SqlHelper.poster_path };
		Cursor cursor = db.query(SqlHelper.movies, columns, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			poster_path.add(cursor.getString(0));
		}
		return poster_path;
	}

	public ArrayList<String> getTitels() {

		ArrayList<String> Titels = new ArrayList<>();
		String[] columns = { SqlHelper.original_title };
		Cursor cursor = db.query(SqlHelper.movies, columns, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			Titels.add(cursor.getString(0));
		}
		return Titels;
	}

	public ArrayList<Integer> getIds() {

		ArrayList<Integer> Ids = new ArrayList<>();
		String[] columns = { SqlHelper.id };
		Cursor cursor = db.query(SqlHelper.movies, columns, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			Ids.add(cursor.getInt(0));
		}
		return Ids;
	}

	public int removeFavorit(ResultFilms films){
		return db.delete(SqlHelper.movies," id = ? ",new String[] { films.getId()+"" });
	}
}
