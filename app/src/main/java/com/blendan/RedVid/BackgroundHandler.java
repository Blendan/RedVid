package com.blendan.RedVid;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class BackgroundHandler extends AsyncTask<Void, Void, Void>
{
	@SuppressLint("StaticFieldLeak")
	private MainActivity main;
	private String url;

	BackgroundHandler(String url, MainActivity main)
	{
		this.url = url;
		this.main = main;
	}

	@Override
	protected Void doInBackground(Void... voids)
	{
		GetJson video = new GetJson(url);

		if (video.isSuccess())
		{
			String name = "v" + (video.getVideoUrl().split("https://v[.]redd[.]it/")[1].split("/")[0]);
			System.out.println("name: " + name);
			Downloader.fileDownload(video.getVideoUrl(), name + ".mp4");
		}
		else
		{
			System.err.println("ERROR");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		/*
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(main);
		mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
		mBuilder.setContentTitle("Notification Alert, Click Me!");
		mBuilder.setContentText("Hi, This is Android Notification Detail!");

		NotificationManager mNotificationManager = (NotificationManager) main.getSystemService(Context.NOTIFICATION_SERVICE);
		// notificationID allows you to update the notification later on.
		if (mNotificationManager != null)
		{
			mNotificationManager.notify(1, mBuilder.build());
			System.out.println("notified");
		}
		*/

		//main.sendNotification("Test","test");
	}


}
