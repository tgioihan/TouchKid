package com.bestfunforever.bearforkids.database;

import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bestfunforever.bearforkids.HighScore;
import com.bestfunforever.bearforkids.HighScoreSorter;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "playota";

	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static DatabaseHelper mInstance = null;

	public synchronized static DatabaseHelper getInstance(Context ctx) {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		// See this article for more information:
		if (mInstance == null) {
			mInstance = new DatabaseHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		createDatabaseAndTables(db);
	}

	public boolean isOpen() {
		SQLiteDatabase db = getReadableDatabase();
		if (db.isOpen()) {
			db.close();
			return true;
		}
		return false;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exist " + HightScoreTable.NAME);
		onCreate(db);
	}

	public static class HightScoreTable {
		public static final String NAME = "HightScoreTable";

		public static void createTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE  " + NAME + "(" + Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
					+ Column.NAME + " TEXT, " + Column.SCORE + " integer, " + Column.level + " integer, "
					+ Column.DATE_CREATED + " long " + ");");
		}

		public class Column {
			public static final String ID = "_id";

			public static final String NAME = "name";

			public static final String SCORE = "score";

			public static final String level = "level";

			public static final String DATE_CREATED = "date";

		}
	}
	
	public ArrayList<HighScore> getHighScore(){
		SQLiteDatabase db= getReadableDatabase();
		ArrayList<HighScore> highScores = getHighScoreFromDb(db);
		db.close();
		return highScores;
	}
	
	private ArrayList<HighScore> getHighScoreFromDb(SQLiteDatabase db){
		Cursor c = db.rawQuery("select * from "+HightScoreTable.NAME, null);
		ArrayList<HighScore> highScores = new ArrayList<HighScore>();
		if(c.moveToFirst()){
			
			do {
				int id = c.getInt(c.getColumnIndex(HightScoreTable.Column.ID));
				int score = c.getInt(c.getColumnIndex(HightScoreTable.Column.SCORE));
				int level = c.getInt(c.getColumnIndex(HightScoreTable.Column.level));
				String name = c.getString(c.getColumnIndex(HightScoreTable.Column.NAME));
				long createTime = c.getLong(c.getColumnIndex(HightScoreTable.Column.DATE_CREATED));
				HighScore highScore = new HighScore(id, name, score, level, createTime);
				highScores.add(highScore);
			} while (c.moveToNext());
		}
		
		if(highScores.size()>0){
			HighScore last = highScores.get(highScores.size()-1);
			Log.d("", "before last highscore "+last);
			Collections.sort(highScores, new HighScoreSorter());
			HighScore mLast = highScores.get(highScores.size()-1);
			Log.d("", "after last highscore "+mLast);
			if(mLast != last){
				updateHightScore(last.getId(), mLast, db);
				updateHightScore(mLast.getId(), last, db);
			}
		}
		c.close();
		return highScores;
	}
	
	public void updateHightScore(int id,HighScore highScore,SQLiteDatabase db){
		ContentValues vl = new ContentValues();
		vl.put(HightScoreTable.Column.NAME, highScore.getName());
		vl.put(HightScoreTable.Column.DATE_CREATED, highScore.getCreatedTime());
		vl.put(HightScoreTable.Column.level, highScore.getLevel());
		vl.put(HightScoreTable.Column.SCORE, highScore.getScore());
		db.update(HightScoreTable.NAME, vl, HightScoreTable.Column.ID+"="+id, null);
	}

	public static final int MAX_HIGHSCORE = 30;
	
	public void insertHighScore(HighScore highScore){
		
		SQLiteDatabase db= getReadableDatabase();
		ArrayList<HighScore> highScores = getHighScoreFromDb(db);
		if(highScores.size()>=MAX_HIGHSCORE){
			Log.d("", "insertHighScore highscore size = "+MAX_HIGHSCORE);
			HighScore last = highScores.get(highScores.size()-1);
			Log.d("", "insertHighScore last highscore "+last);
			updateHightScore(last.getId(), highScore, db);
		}else{
			Log.d("", "insertHighScore highscore size < "+MAX_HIGHSCORE);
			ContentValues vl = new ContentValues();
			vl.put(HightScoreTable.Column.NAME, highScore.getName());
			vl.put(HightScoreTable.Column.DATE_CREATED, highScore.getCreatedTime());
			vl.put(HightScoreTable.Column.level, highScore.getLevel());
			vl.put(HightScoreTable.Column.SCORE, highScore.getScore());
			db.insert(HightScoreTable.NAME,null, vl);
		}
		db.close();
	}
	
	private void createDatabaseAndTables(SQLiteDatabase db) {
		HightScoreTable.createTable(db);
	}

}