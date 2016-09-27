package com.victony.gettutors.bean;

public class VideoItem
{
	private String title;
	private String description;
	private String thumbnailURL;
	private String id;
	private String publishDate;
	private int viewCount;
	private int likeCount;
	private int dislikeCount;
	private boolean isFavorite;
	
	
	public String getTitle()
	{
		return title;
	}
	
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	
	public String getDescription()
	{
		return description;
	}
	
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	public String getThumbnailURL()
	{
		return thumbnailURL;
	}
	
	
	public void setThumbnailURL(String thumbnailURL)
	{
		this.thumbnailURL = thumbnailURL;
	}
	
	
	public String getId()
	{
		return id;
	}
	
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	public String getPublishDate()
	{
		return publishDate;
	}
	
	
	public void setPublishDate(String publishDate)
	{
		this.publishDate = publishDate;
	}
	
	
	public int getViewCount()
	{
		return viewCount;
	}
	
	
	public void setViewCount(int viewCount)
	{
		this.viewCount = viewCount;
	}
	
	
	public int getLikeCount()
	{
		return likeCount;
	}
	
	
	public void setLikeCount(int likeCount)
	{
		this.likeCount = likeCount;
	}
	
	
	public int getDislikeCount()
	{
		return dislikeCount;
	}
	
	
	public void setDislikeCount(int dislikeCount)
	{
		this.dislikeCount = dislikeCount;
	}
	
	
	public boolean isFavorite()
	{
		return isFavorite;
	}
	
	
	public void setFavorite(boolean favorite)
	{
		isFavorite = favorite;
	}
}
