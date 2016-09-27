package com.victony.gettutors.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.victony.gettutors.R;
import com.victony.gettutors.activity.GlobalApplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class NetworkUtil
{
	private static final int DEFAULT_TIME_OUT = 20000;
	private static final int DEFAULT_RETRIES = 0;
	private static NetworkUtil instance = new NetworkUtil();
	
	
	public static NetworkUtil getInstance()
	{
		return instance;
	}
	
	
	public void requestVolleyGet(final Context context, String requestUrl, com.android.volley.Response.Listener<String> response)
	{
		requestVolleyGet(requestUrl, response, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				Log.d("error", error.toString());
				Toast.makeText(context, R.string.text_connect_failed, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	public void requestVolleyGet(String requestUrl, com.android.volley.Response.Listener<String> response,
			com.android.volley.Response.ErrorListener errorListener)
	{
		Log.d("requestUrl", requestUrl);
		if (response == null)
			response = new Response.Listener<String>()
			{
				@Override
				public void onResponse(String response)
				{
					
				}
			};
		if (errorListener == null)
			errorListener = new Response.ErrorListener()
			{
				@Override
				public void onErrorResponse(VolleyError error)
				{
					
				}
			};
		StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl, response, errorListener);
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIME_OUT, DEFAULT_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		GlobalApplication.getInstance().getRequestQueue().add(stringRequest);
	}
	
	
	public void requestVolleyPost(String requestUrl, com.android.volley.Response.Listener<String> response,
			com.android.volley.Response.ErrorListener errorListener, final HashMap<String, String> params, String[] keys)
	{
		String url = requestUrl;
		url += "?";
		for (String key : keys)
			url += key + "=" + params.get(key) + "&";
		Log.d("requestUrl", url);
		requestVolleyPost(requestUrl, response, errorListener, params);
	}
	
	
	public void requestVolleyPost(String requestUrl, com.android.volley.Response.Listener<String> response,
			com.android.volley.Response.ErrorListener errorListener, final HashMap<String, String> params)
	{
		if (response == null)
			response = new Response.Listener<String>()
			{
				@Override
				public void onResponse(String response)
				{
					
				}
			};
		if (errorListener == null)
			errorListener = new Response.ErrorListener()
			{
				@Override
				public void onErrorResponse(VolleyError error)
				{
					
				}
			};
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, response, errorListener)
		{
			@Override
			protected HashMap<String, String> getParams()
			{
				return params;
			}
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIME_OUT, DEFAULT_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		GlobalApplication.getInstance().getRequestQueue().add(stringRequest);
	}
	
	
	public String requestGet(String requestURL)
	{
		Log.i("url", requestURL);
		String response = null;
		try
		{
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEFAULT_TIME_OUT);
			conn.setReadTimeout(DEFAULT_TIME_OUT);
			
			InputStream inputStream = conn.getInputStream();
			int statusCode = conn.getResponseCode();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder message = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null)
			{
				message.append(line);
			}
			
			response = message.toString();
			Log.i("*******", "*********** Response : " + response);
			inputStream.close();
			conn.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
	
	
	public String requestPost(String siteUrl)
	{
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String response = "";
		try
		{
			URL url = new URL(siteUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEFAULT_TIME_OUT);
			conn.setReadTimeout(DEFAULT_TIME_OUT);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****;charset=utf-8");
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			
//            //first parameter
//            dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"GROUP_ID\"" + lineEnd + lineEnd + "PGKR");
//
//            //second parameter
//            dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"MEM_SEQ\"" + lineEnd + lineEnd + userBean.getU_idx());
//
//            //third parameter
//            dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"cmd\"" + lineEnd + lineEnd + "mem_pic");
//
//            //forth parameter
//            dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"MEM_IMAGE\";filename=\"" + "image.png" + "\"" + lineEnd);
//            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd + lineEnd);
			
//            int bytesRead, bytesAvailable, bufferSize;
//            byte[] buffer;
//            int maxBufferSize = 1024 * 1024;
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            userBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//            byte[] imageBytes = bos.toByteArray();
//
//            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(imageBytes);
//            bytesAvailable = fileInputStream.available();
//
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            while (bytesRead > 0)
//            {
//                dos.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            }
			
			dos.writeBytes(lineEnd + twoHyphens + boundary + twoHyphens + lineEnd);
			dos.flush();
			
			InputStream inputStream = conn.getInputStream();
			int statusCode = conn.getResponseCode();
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder message = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null)
			{
				message.append(line);
			}
			
			response = message.toString();
			
			dos.close();
			inputStream.close();
			conn.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
	
	
	public void showErrorToast(Context context)
	{
		Toast.makeText(context, R.string.text_connect_failed, Toast.LENGTH_SHORT).show();
	}
}
