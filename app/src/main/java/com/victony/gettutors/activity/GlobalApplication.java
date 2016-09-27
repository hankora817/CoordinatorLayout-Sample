package com.victony.gettutors.activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class GlobalApplication extends Application
{
	private static volatile GlobalApplication instance = null;
	private RequestQueue mRequestQueue;
	
	
	public static GlobalApplication getInstance()
	{
		if (instance == null)
			throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
		return instance;
	}
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
		
		mRequestQueue = getRequestQueue();
		ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache()
		{
			final LruCache<String, Bitmap> imageCache = new LruCache<>(20);
			
			
			@Override
			public void putBitmap(String key, Bitmap value)
			{
				imageCache.put(key, value);
			}
			
			
			@Override
			public Bitmap getBitmap(String key)
			{
				return imageCache.get(key);
			}
		};
	}
	
	
	public RequestQueue getRequestQueue()
	{
		if (mRequestQueue == null)
			mRequestQueue = Volley.newRequestQueue(this);
		return mRequestQueue;
	}
	
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		instance = null;
	}
}
