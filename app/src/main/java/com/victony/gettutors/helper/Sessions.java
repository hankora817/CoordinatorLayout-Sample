package com.victony.gettutors.helper;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Sessions
{
	
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	private static final String NAME_PREFERANCES = "YouTubePrefercences";
	private static final String IS_LOGIN = "IsLoggedIn";
	private static final String TOKEN = "Token";
	private static final String LAST_REFRESH = "Last Refresh";
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor theEditor;
	Context theContext;
	
	
	public Sessions(Context context)
	{
		this.theContext = context;
		sharedPreferences = theContext.getSharedPreferences(NAME_PREFERANCES, Context.MODE_PRIVATE);
		theEditor = sharedPreferences.edit();
	}
	
	
	public void createLoginSession(String name, String email)
	{
		theEditor.putBoolean(IS_LOGIN, true);
		theEditor.putString(NAME, name);
		theEditor.putString(EMAIL, email);
		theEditor.commit();
	}
	
	
	public String getToken()
	{
		String token = sharedPreferences.getString(TOKEN, "");
		return token;
	}
	
	
	public void setToken(String token)
	{
		Log.d("token", "Token set :" + token);
		theEditor.putString(TOKEN, token);
		long date = new Date(System.currentTimeMillis()).getTime();
		theEditor.putLong(LAST_REFRESH, date);
		theEditor.commit();
	}
	
	
	public String getLoggedInMail()
	{
		return sharedPreferences.getString(EMAIL, "");
	}
	
	
	public void clearUserDetails()
	{
		theEditor.putBoolean(IS_LOGIN, false);
		theEditor.putString(NAME, "");
		theEditor.putString(EMAIL, "");
		theEditor.putString(TOKEN, "");
		theEditor.commit();
	}
	
	
	public boolean isLoggedIn()
	{
		return sharedPreferences.getBoolean(IS_LOGIN, false);
	}
}
