package com.blendan.RedVid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ListView listView = findViewById(R.id.listDownloads);
		listView.setAdapter(DownloadListHandler.getListDownload(this));

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED)
		{
			requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
		}
		else
		{
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED)
			{
				requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
			}
		}

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null)
		{
			if ("text/plain".equals(type))
			{

				if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED)
				{
					requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
				}
				else
				{
					if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
							!= PackageManager.PERMISSION_GRANTED)
					{
						requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 2);
					}
					else
					{
						finish();

						Toast.makeText(this, "Download started", Toast.LENGTH_LONG).show();

						final String url = intent.getStringExtra(Intent.EXTRA_TEXT);
						System.out.println(url);
						new BackgroundHandler(url, this).execute();
					}
				}
			}
		}
	}

	@SuppressWarnings("SameParameterValue")
	private void requestPermission(String permissionName, int permissionRequestCode)
	{
		ActivityCompat.requestPermissions(this, new String[]{permissionName}, permissionRequestCode);
	}
}
