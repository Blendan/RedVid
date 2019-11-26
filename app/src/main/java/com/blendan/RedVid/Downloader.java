package com.blendan.RedVid;

import android.os.Environment;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

class Downloader
{
	private final static int size = 1024;
	private final static boolean debug = true;
	private final static String folder = "RedVid";

	private static String fileUrl(String fAddress, String destinationDir, boolean toTemp)
	{
		OutputStream outStream = null;
		URLConnection uCon;
		String outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folder + (toTemp ? "_temp/" : "/") + destinationDir;

		InputStream is = null;
		try
		{
			URL Url;
			byte[] buf;
			int ByteRead, ByteWritten = 0;
			Url = new URL(fAddress);
			System.out.println(fAddress);
			System.out.println("\\\\//");

			File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folder + (toTemp ? "_temp/" : "/"));

			if (!f.isDirectory())
			{
				//noinspection ResultOfMethodCallIgnored
				f.mkdirs();

				System.out.println("dir made: " + f.getAbsolutePath());
			}
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
			return outDir;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{

				if (is != null)
				{
					is.close();
				}
				if (outStream != null)
				{
					outStream.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("SameParameterValue")
	static void fileDownload(String fAddress, String destinationDir, boolean isGif)
	{

		int slashIndex = fAddress.lastIndexOf('/');
		int periodIndex = fAddress.lastIndexOf('.');

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1)
		{
			if (isExternalStorageWritable())
			{
				if (!isGif)
				{
					String outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folder + "/" + destinationDir;


					String video = fileUrl(fAddress, "video_" + destinationDir, true);
					String audio = fileUrl(fAddress.split("/DASH_")[0] + "/audio?source=fallback", "audio_" + destinationDir ,true);

					try
					{
						merge(outDir, video, audio);
					} catch (IOException e)
					{
						e.printStackTrace();
					}

					cleanup(video, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folder + "_temp/" + "audio_" + destinationDir);
				}
				else
				{
					fileUrl(fAddress, destinationDir, false);
				}

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

	private static void merge(String outDir, String videoDir, String audioDir) throws IOException
	{
		File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folder);

		if (!f.isDirectory())
		{
			//noinspection ResultOfMethodCallIgnored
			f.mkdirs();

			System.out.println("dir made: " + f.getAbsolutePath());
		}

		Movie video = MovieCreator.build(videoDir);
		Movie audio = null;

		if (audioDir != null)
		{
			audio = MovieCreator.build(audioDir);
		}

		List<Track> videoTracks = video.getTracks();
		video.setTracks(new LinkedList<Track>());

		List<Track> audioTracks = null;
		if (audio != null)
		{
			audioTracks = audio.getTracks();
		}

		for (Track videoTrack : videoTracks)
		{
			video.addTrack(new AppendTrack(videoTrack));
		}
		if (audioTracks != null)
		{
			for (Track audioTrack : audioTracks)
			{
				video.addTrack(new AppendTrack(audioTrack));
			}
		}

		Container mp4file = new DefaultMp4Builder().build(video);

		//Write the container to an appropriate sink.

		FileChannel fc = new FileOutputStream(new File(outDir)).getChannel();
		mp4file.writeContainer(fc);
		fc.close();
	}

	private static void cleanup(String video, String audio)
	{
		File fDelete = new File(video);
		if (fDelete.exists())
		{
			if (fDelete.delete())
			{
				System.out.println("file Deleted :" + video);
			}
			else
			{
				System.out.println("file not Deleted :" + video);
			}
		}

		if (audio != null)
		{
			fDelete = new File(audio);
			if (fDelete.exists())
			{
				if (fDelete.delete())
				{
					System.out.println("file Deleted :" + audio);
				}
				else
				{
					System.out.println("file not Deleted :" + audio);
				}
			}
		}
	}
}
