package com.blendan.RedVid;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListDownload extends ArrayAdapter<StatusDownload>
{
	private final AppCompatActivity context;
	private final ArrayList<StatusDownload> objects = DownloadListHandler.getStatusDownloads();

	ListDownload(AppCompatActivity context, int resource)
	{
		super(context, resource, DownloadListHandler.getStatusDownloads());
		this.context = context;
	}

	@SuppressWarnings("NullableProblems")
	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		StatusDownload current = objects.get(position);
		LayoutInflater inflater = context.getLayoutInflater();
		@SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.activity_list_download, null, true);
		TextView txtName = rowView.findViewById(R.id.name);
		TextView txtStatus = rowView.findViewById(R.id.status);
		txtName.setText(current.getName());
		txtStatus.setText(current.getStatus());

		int statusColor = Color.GRAY;
		if (current.getStatus().equals("FINISHED"))
		{
			statusColor = Color.GREEN;
		}
		else if (current.getStatus().equals("FAILED"))
		{
			statusColor = Color.RED;
		}

		txtStatus.setTextColor(statusColor);

		return rowView;
	}
}
