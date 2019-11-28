package com.blendan.RedVid;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BackgroundHandler extends AsyncTask<Void, Void, Boolean>
{
	@SuppressLint("StaticFieldLeak")
	private AppCompatActivity main;
	private String url;
	private StatusDownload status;

	BackgroundHandler(String url, AppCompatActivity main)
	{
		this.url = url;
		this.main = main;
	}

	@Override
	protected void onPreExecute()
	{
		status = DownloadListHandler.getNewStatus(url, main);
	}

	@Override
	protected Boolean doInBackground(Void... voids)
	{
		GetJson video = new GetJson(url);

		if (video.isSuccess())
		{
			String name;
			if (!video.isGif())
			{
				System.out.println(video.getVideoUrl());
				name = "v" + (video.getVideoUrl().split("https://v[.]redd[.]it/")[1].split("/")[0]);
				name += ".mp4";
			}
			else
			{
				name = "g" + (video.getVideoUrl().split("https://i[.]redd[.]it/")[1].split("/")[0]);
			}

			System.out.println("name: " + name);

			status.setName(name);
			status.setUrl(video.getVideoUrl());

			Downloader.fileDownload(video.getVideoUrl(), name, video.isGif());
		}
		else
		{
			System.err.println("ERROR");
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean aVoid)
	{
		super.onPostExecute(aVoid);

		if (aVoid)
		{
			Toast.makeText(main, "Download Complete", Toast.LENGTH_LONG).show();
			status.success();
		}
		else
		{
			Toast.makeText(main, "Download Failed", Toast.LENGTH_LONG).show();
			status.failed();
		}

		DownloadListHandler.refresh(main);
	}
}
