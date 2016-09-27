package com.victony.gettutors.activity;

import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.victony.gettutors.R;
import com.victony.gettutors.YouTubeFragment;
import com.victony.gettutors.bean.VideoItem;
import com.victony.gettutors.controller.MainAdapter;
import com.victony.gettutors.helper.Sessions;
import com.victony.gettutors.helper.YouTubeConnector;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private final String TAB_TITLE_YOUTUBE = "YouTube";
	private ViewPager mViewPager;
	private Sessions session;
	private MainAdapter mMainAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		AppBarLayout.LayoutParams toolbarParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
		toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
		
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mMainAdapter = new MainAdapter(getSupportFragmentManager());
		
		mMainAdapter.addFragment(new YouTubeFragment(), TAB_TITLE_YOUTUBE);
		mViewPager.setAdapter(mMainAdapter);
		
		TabLayout tabsContainer = (TabLayout) findViewById(R.id.tabsContainer);
		tabsContainer.setTabMode(TabLayout.MODE_FIXED);
		tabsContainer.setupWithViewPager(mViewPager);
		tabsContainer.setSelectedTabIndicatorColor(Color.TRANSPARENT);
		
		AppBarLayout.LayoutParams tabsParams = (AppBarLayout.LayoutParams) tabsContainer.getLayoutParams();
		// Hide Tabs when is scrolling to down of the list, and show back when the scroll is to up
//        tabsParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
		
		// Hide Tabs when is scrolling to down of the list, and show back when the scroll is to up and the next item to show is the first
//        tabsParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
		
		session = new Sessions(this);
		Log.d("token", "Token:" + session.getToken());
		
//		new GetFavoriteVideos().execute();
	}
	
	private class GetFavoriteVideos extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			try
			{
				YouTubeConnector yc = new YouTubeConnector(MainActivity.this);
				List<VideoItem> favoriteResults = yc.getFavoriteVideos();
				Log.d("favorit", String.valueOf(favoriteResults));
			}
			catch (GoogleJsonResponseException e)
			{
				Log.d("debug", session.getLoggedInMail());
				Log.d("debug", String.valueOf(e.getDetails().getMessage()));
				if (e.getDetails().getCode() == 401)
				{
					session.clearUserDetails();
					startActivity(new Intent(MainActivity.this, LauncherActivity.class));
					finish();
				}
				else if (e.getDetails().getMessage().equalsIgnoreCase("channel not found."))
				{
					runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							Toast.makeText(MainActivity.this, "채널이 없습니다.", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return "";
		}
		
		
		@Override
		protected void onPostExecute(String response)
		{
			super.onPostExecute(response);
		}
	}
}
