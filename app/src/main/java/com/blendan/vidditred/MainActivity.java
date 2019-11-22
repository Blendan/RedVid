package com.blendan.vidditred;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null)
		{
			if ("text/plain".equals(type))
			{
				System.out.println(intent.getStringExtra(Intent.EXTRA_TEXT));
				Downloader.fileDownload("https://v.redd.it/zxb48usmsb821/audio?source=fallback", "test.mp4");;
			}
		}
	}
}
