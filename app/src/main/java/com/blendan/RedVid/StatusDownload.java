package com.blendan.RedVid;

public class StatusDownload
{
	private String url, name, status;

	StatusDownload(String url, String name)
	{
		this.url = url;
		this.name = name;
		this.status = "Downloading ...";
	}

	void failed()
	{
		this.status = "FAILED";
	}

	void success()
	{
		this.status="FINISHED";
	}

	public String getUrl()
	{
		return url;
	}

	public String getName()
	{
		return name;
	}

	String getStatus()
	{
		return status;
	}

	void setUrl(String url)
	{
		this.url = url;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
