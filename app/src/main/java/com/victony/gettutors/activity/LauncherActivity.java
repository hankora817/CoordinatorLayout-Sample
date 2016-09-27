package com.victony.gettutors.activity;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.services.youtube.YouTubeScopes;
import com.victony.gettutors.R;
import com.victony.gettutors.helper.Sessions;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LauncherActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
	private static final int REQUEST_CODE_PICK_ACCOUNT = 0;
	private static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	
	private GoogleApiClient mGoogleApiClient;
	private Sessions session;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		session = new Sessions(getApplicationContext());
		if (session.isLoggedIn())
		{
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
		else
		{
			setContentView(R.layout.activity_launcher);
			
			mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
																.addOnConnectionFailedListener(this)
																.addApi(Plus.API)
																.addScope(new Scope(Scopes.PROFILE))
																.addScope(new Scope(Scopes.EMAIL))
																.addScope(new Scope(YouTubeScopes.YOUTUBE))
																.build();
			
			findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					onSignInClicked();
				}
			});
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_PICK_ACCOUNT && resultCode == Activity.RESULT_OK)
		{
			mGoogleApiClient.connect();
		}
		else if (requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR)
		{
			new GetAccessToken().execute();
		}
	}
	
	
	private void onSignInClicked()
	{
		String[] accountTypes = new String[] { GoogleAccountManager.ACCOUNT_TYPE };
		Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
		startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
	}
	
	
	private void onSignOutClicked()
	{
		if (mGoogleApiClient.isConnected())
		{
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
		}
	}
	
	
	@Override
	protected void onStart()
	{
		super.onStart();
		if (mGoogleApiClient != null)
			mGoogleApiClient.connect();
	}
	
	
	@Override
	protected void onStop()
	{
		super.onStop();
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
			mGoogleApiClient.disconnect();
	}
	
	
	@Override
	public void onConnected(Bundle bundle)
	{
		Log.d("debug", "connected");
		if (!session.isLoggedIn())
		{
			session.createLoginSession("User", Plus.AccountApi.getAccountName(mGoogleApiClient));
			new GetAccessToken().execute();
			Log.d("debug", "createSession");
		}
		else
		{
			onSignOutClicked();
			session.clearUserDetails();
			Log.d("debug", "signOut");
		}
	}
	
	
	@Override
	public void onConnectionSuspended(int i)
	{
		
	}
	
	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult)
	{
		if (connectionResult.hasResolution())
		{
			try
			{
				connectionResult.startResolutionForResult(this, REQUEST_CODE_PICK_ACCOUNT);
			}
			catch (IntentSender.SendIntentException e)
			{
				e.printStackTrace();
				mGoogleApiClient.connect();
			}
		}
	}
	
	private class GetAccessToken extends AsyncTask<String, String, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			String accessToken = null;
			try
			{
				accessToken = GoogleAuthUtil.getToken(getApplicationContext(), session.getLoggedInMail(), "oauth2:" + Scopes.PROFILE + " "
						+ YouTubeScopes.YOUTUBE + " " + Scopes.EMAIL);
				session.setToken(accessToken);
			}
			catch (UserRecoverableAuthException e)
			{
				startActivityForResult(e.getIntent(), REQUEST_CODE_RECOVER_FROM_AUTH_ERROR);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return accessToken;
		}
		
		
		@Override
		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
			startActivity(new Intent(LauncherActivity.this, MainActivity.class));
			finish();
		}
	}
}
