package com.example.nick.hsvcolor;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Nick on 4/21/2015.
 */
public class HSVColorAdapter extends ArrayAdapter<HSVColor> {
    private final int layoutID;
    private final int listItemID;

    public HSVColorAdapter(Context context, List<HSVColor> objects, int layoutID, int listItemID){
        super(context, 0, objects);

        this.layoutID = layoutID;
        this.listItemID = listItemID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Inflate view if null
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutID, parent, false);
        }

        //Populate the (sub)views
        HSVColor currentColor = getItem(position);
        HSVColor nextColor = getItem((position + 1) % getCount()); // BUG FOR SATURATION AND VALUE

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {currentColor.toInt(), nextColor.toInt()});

        View listItem = convertView.findViewById(listItemID);
        listItem.setBackground(drawable);

        return convertView;
    }
}
