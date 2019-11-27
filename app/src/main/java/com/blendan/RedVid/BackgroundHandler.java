package com.blendan.RedVid;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

public class BackgroundHandler extends AsyncTask<Void, Void, Boolean>
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
	protected Boolean doInBackground(Void... voids)
	{
		GetJson video = new GetJson(url);

		if (video.isSuccess())
		{
			String name = "";
			if (!video.isGif())
			{
				name = "v" + (video.getVideoUrl().split("https://v[.]redd[.]it/")[1].split("/")[0]);
				name += ".mp4";
			}
			else
			{
				name = "g" + (video.getVideoUrl().split("https://i[.]redd[.]it/")[1].split("/")[0]);
			}

			System.out.println("name: " + name);

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

		if (aVoid)
		{
			Toast.makeText(main, "Download Complete", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(main, "Download Failed", Toast.LENGTH_LONG).show();
		}
	}


}
