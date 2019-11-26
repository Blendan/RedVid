package com.blendan.vidditred;

import android.os.Environment;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

class Downloader
{
	private final static int size = 1024;
	private final static boolean debug = true;

	private static void fileUrl(String fAddress, String destinationDir)
	{
		OutputStream outStream = null;
		URLConnection uCon;

		InputStream is = null;
		try
		{
			URL Url;
			byte[] buf;
			int ByteRead, ByteWritten = 0;
			Url = new URL(fAddress);
			System.out.println(fAddress);
			System.out.println("\\\\//");
			@SuppressWarnings("deprecation")
			String outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ViddtRed/" + destinationDir;
			System.out.println(outDir);
			outStream = new BufferedOutputStream(new FileOutputStream(outDir));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			int length = is.available();
			int percent = 0;
			buf = new byte[size];
			System.out.println("Started Download");

			for (int i = 0; i < 100; i++)
			{
				System.out.print("_");
			}
			System.out.print("\n");


			while ((ByteRead = is.read(buf)) != -1)
			{
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
				if (debug)
				{
					if (Math.floor(((float) ByteWritten / (float) length) * 100) > percent)
					{
						percent++;
						System.out.print("=");
					}
				}
			}
			System.out.print("\n");
			System.out.println("Downloaded Successfully.");
			System.out.println("File name:\"" + destinationDir + "\"\nNo ofbytes :" + ByteWritten);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				assert is != null;
				is.close();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("SameParameterValue")
	static void fileDownload(String fAddress, String destinationDir)
	{

		int slashIndex = fAddress.lastIndexOf('/');
		int periodIndex = fAddress.lastIndexOf('.');

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1)
		{
			if (isExternalStorageWritable())
			{
				fileUrl(fAddress, destinationDir);
			}
			else
			{
				System.err.println("External storage not available");
			}
		}
		else
		{
			System.err.println("path or file name.");
		}
	}

	private static boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
}
