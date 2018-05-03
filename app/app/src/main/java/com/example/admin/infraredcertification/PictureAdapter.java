package com.example.admin.infraredcertification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/06/01 0001.
 */

public class PictureAdapter extends ArrayAdapter<PadyPicture> {
    public int resourceId;
    public PictureAdapter(Context context , int textViewId, List<PadyPicture> objects)
    {
        super(context,textViewId,objects);
        resourceId=textViewId;
    }
    public View getView(int position , View convertView, ViewGroup parent)
    {
        PadyPicture padyPicture=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView titleView=(TextView)view.findViewById(R.id.tv_picture);
        titleView.setText(padyPicture.getTitle());
        return view;
    }
}
