package com.victony.gettutors.utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonPaser
{
	private static JsonPaser jsonPaser = new JsonPaser();
	
	
	public static JsonPaser getInstance()
	{
		return jsonPaser;
	}
	
	
	//result Only값
	public ArrayList<String> getResultOnly(String response)
	{
		ArrayList<String> resultArr = new ArrayList<>();
		try
		{
			JSONObject object = new JSONObject(response);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Integer getTotalPage(String response)
	{
		int count = 0;
		try
		{
			JSONObject object = new JSONObject(response);
			count = object.getInt("tpage");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	public Integer getItemCount(String response)
	{
		int count = 0;
		try
		{
			JSONObject object = new JSONObject(response);
			count = object.getInt("cnt");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return count;
	}
	
//	public ArrayList<CouponBean> getCouponArray(String response)
//	{
//		ArrayList<CouponBean> couponArray = new ArrayList<CouponBean>();
//
//		try
//		{
//			JSONArray mArray = new JSONObject(response).getJSONArray("list");
//			for (int i = 0; i < mArray.length(); i++)
//			{
//				CouponBean bean = new CouponBean();
//				JSONObject object = mArray.getJSONObject(i);
//				bean.setCoupon_seq(object.getString("COUPON_SEQ"));
//				bean.setGroup_id(object.getString("GROUP_ID"));
//				bean.setCoupon_cd(object.getString("COUPON_CD"));
//				bean.setCoupon_nm(object.getString("COUPON_NM"));
//				bean.setComment(object.getString("COMMENT"));
//				bean.setType(object.getString("TYPE"));
//				bean.setFr_dt(object.getString("FR_DT"));
//				bean.setFr_tm(object.getString("FR_TM"));
//				bean.setTo_dt(object.getString("TO_DT"));
//				bean.setTo_tm(object.getString("TO_TM"));
//				bean.setDc_type(object.getString("DC_TYPE"));
//				bean.setDc_rate(object.getString("DC_RATE"));
//				bean.setDc_limit(object.getString("DC_LIMIT"));
//				bean.setDc_base(object.getString("DC_BASE"));
//				bean.setReg_dt(object.getString("REG_DT"));
//				bean.setImage1(object.getString("IMAGE1"));
//				bean.setImg_cnt(object.getInt("IMG_CNT"));
//				couponArray.add(bean);
//			}
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//
//		return couponArray;
//	}
}
