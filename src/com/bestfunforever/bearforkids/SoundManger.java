package com.bestfunforever.bearforkids;

import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SoundManger {
	
	public static final String SOUND_KEY = "sound";
	public static final String MUSIC_KEY = "music";

	public static boolean isSoundEnable(SharedPreferences preferences){
		
		return preferences.getBoolean(SOUND_KEY, true);
	}
	
	public static boolean isMusicEnable(SharedPreferences preferences){
		return preferences.getBoolean(MUSIC_KEY, true);
	}
	
	public static void setSoundEnable(SharedPreferences preferences,boolean enable){
		Editor edit = preferences.edit();
		edit.putBoolean(SOUND_KEY, enable);
		edit.commit();
	}
	
	public static void setMusicEnable(SharedPreferences preferences, boolean enable){
		Editor edit = preferences.edit();
		edit.putBoolean(MUSIC_KEY, enable);
		edit.commit();
	}

	public static boolean isSoundEnable(SimpleBaseGameActivity context) {
		// TODO Auto-generated method stub
		SharedPreferences preferences =context. getSharedPreferences("BearforKid", 0);
		return isSoundEnable(preferences);
	}
	
	public static boolean isMusicEnable(SimpleBaseGameActivity context){
		SharedPreferences preferences =context. getSharedPreferences("BearforKid", 0);
		return isMusicEnable(preferences);
	}
	
	public static void setSoundEnable(SimpleBaseGameActivity context,boolean enable){
		SharedPreferences preferences =context. getSharedPreferences("BearforKid", 0);
		Editor edit = preferences.edit();
		edit.putBoolean(SOUND_KEY, enable);
		edit.commit();
	}
	
	public static void setMusicEnable(SimpleBaseGameActivity context, boolean enable){
		SharedPreferences preferences =context. getSharedPreferences("BearforKid", 0);
		Editor edit = preferences.edit();
		edit.putBoolean(MUSIC_KEY, enable);
		edit.commit();
	}

}
