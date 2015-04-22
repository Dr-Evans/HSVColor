package com.example.nick.hsvcolor.arrayadapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.nick.hsvcolor.fragment.HSVColorGradient;

import java.util.List;

/**
 * Created by Nick on 4/21/2015.
 */
public class HSVColorGradientAdapter extends ArrayAdapter<HSVColorGradient> {
    private final int layoutID;
    private final int listItemID;

    public HSVColorGradientAdapter(Context context, List<HSVColorGradient> objects, int layoutID, int listItemID){
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
        HSVColorGradient currentColorGradient = getItem(position);

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, currentColorGradient.toIntArray());

        View listItem = convertView.findViewById(listItemID);
        listItem.setBackground(drawable);

        return convertView;
    }
}
