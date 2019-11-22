package com.blendan.vidditred;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader
{
	private final static int size = 1024;

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
			System.out.println(destinationDir);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1)
			{
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
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

	public static void fileDownload(String fAddress, String destinationDir)
	{

		int slashIndex = fAddress.lastIndexOf('/');
		int periodIndex = fAddress.lastIndexOf('.');

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1)
		{
			fileUrl(fAddress, destinationDir);
		}
		else
		{
			System.err.println("path or file name.");
		}
	}

	public static void main(String[] args)
	{

		fileDownload("https://v.redd.it/zxb48usmsb821/audio?source=fallback", "./test.mp4");
	}
}
