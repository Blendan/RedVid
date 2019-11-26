package com.blendan.RedVid;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class GetJson
{
	private boolean isGif = false;
	private boolean success = false;
	private String videoUrl;

	public GetJson(String url)
	{
		try
		{
			url = url.split("[/][?]")[0] + "/.json";

			JSONObject json = readJsonFromUrl(url); //"https://www.reddit.com/r/Rainbow6/comments/acdzqr/all_i_was_thinking_was_how_i_needed_to_keep_my/.json");
			assert json != null;
			JSONObject data = (JSONObject) json.get("data");
			JSONArray children = (JSONArray) data.get("children");
			JSONObject childrenData = (JSONObject) ((JSONObject) children.get(0)).get("data"); //

			if(childrenData.get("post_hint").equals("hosted:video"))
			{
				JSONObject media = (JSONObject) childrenData.get("media");
				JSONObject reddit_video = (JSONObject) media.get("reddit_video");
				this.videoUrl = (String) reddit_video.get("fallback_url");
				System.out.println(videoUrl);
				this.isGif = false;
			}
			else if(childrenData.get("post_hint").equals("image"))
			{
				this.isGif = true;
				this.videoUrl = (String) childrenData.get("url");
			}


			this.success = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String readAll(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONObject readJsonFromUrl(@SuppressWarnings("SameParameterValue") String url)
	{
		System.out.println("URL: "+ url);

		try
		{
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			try (InputStream is = connection.getInputStream())
			{
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				String jsonText = readAll(rd);
				//System.out.println("{"+jsonText+"}");
				return new JSONObject(jsonText.substring(1, jsonText.length() - 1));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public boolean isGif()
	{
		return isGif;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public String getVideoUrl()
	{
		return videoUrl;
	}
}
