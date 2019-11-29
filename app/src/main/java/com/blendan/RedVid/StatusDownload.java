package com.blendan.RedVid;

public class StatusDownload
{
	private String url, name, status, error;

	StatusDownload(String url, String name)
	{
		this.url = url;
		this.name = name;
		this.status = "Connecting...";
	}

	void failed()
	{
		this.status = "FAILED";
	}

	void success()
	{
		this.status = "FINISHED";
	}

	void downloading()
	{
		this.status = "Downloading...";
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

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
