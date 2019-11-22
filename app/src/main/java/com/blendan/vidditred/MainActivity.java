package com.blendan.vidditred;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

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
			}
		}
	}

	public boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
}
