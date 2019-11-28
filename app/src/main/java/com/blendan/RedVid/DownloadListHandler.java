package com.blendan.RedVid;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

class DownloadListHandler
{
	private static ArrayList<StatusDownload> statusDownloads = new ArrayList<>();
	private static ListDownload listDownload;

	static StatusDownload getNewStatus(String url, AppCompatActivity context)
	{
		StatusDownload temp = new StatusDownload(url, "loading...");
		getListDownload(context).add(temp);
		return temp;
	}

	static ListDownload getListDownload(AppCompatActivity context)
	{
		if(listDownload==null)
		{
			listDownload = new ListDownload(context, R.layout.activity_list_download);
		}
		return listDownload;
	}

	static ArrayList<StatusDownload> getStatusDownloads()
	{
		return statusDownloads;
	}

	static void refresh(AppCompatActivity context)
	{
		ArrayList<StatusDownload> temp = new ArrayList<>(statusDownloads);
		getListDownload(context).clear();
		getListDownload(context).addAll(temp);
	}
}
