package com.victony.gettutors.controller;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter
{
	private final List<Fragment> mFragments = new ArrayList<>();
	private final List<String> mFragmentTitles = new ArrayList<>();
	
	
	public MainAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	
	public void addFragment(Fragment fragment, String title)
	{
		mFragments.add(fragment);
		mFragmentTitles.add(title);
	}
	
	
	@Override
	public Fragment getItem(int position)
	{
		return mFragments.get(position);
	}
	
	
	@Override
	public int getCount()
	{
		return mFragments.size();
	}
	
	
	public String getPageTitle(int position)
	{
		return mFragmentTitles.get(position);
	}
	
	
	public Fragment getFragmentFromTitle(String title)
	{
		for (int i = 0; i < mFragmentTitles.size(); i++)
		{
			if (mFragmentTitles.get(i).equalsIgnoreCase(title))
				return mFragments.get(i);
		}
		
		return mFragments.get(0);
	}
	
	
	public int getPositionFromTitle(String title)
	{
		for (int i = 0; i < mFragmentTitles.size(); i++)
		{
			if (mFragmentTitles.get(i).equalsIgnoreCase(title))
				return i;
		}
		return 0;
	}
}
