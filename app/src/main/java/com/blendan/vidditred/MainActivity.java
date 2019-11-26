package com.blendan.vidditred;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity
{

	@SuppressLint("StaticFieldLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

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
						requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
					}
					else
					{
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
