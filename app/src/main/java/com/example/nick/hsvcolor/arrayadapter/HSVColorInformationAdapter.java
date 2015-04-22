package com.example.nick.hsvcolor.arrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nick.hsvcolor.fragment.HSVColor;
import com.example.nick.hsvcolor.R;

import java.util.List;

/**
 * Created by Nick on 4/22/2015.
 */
public class HSVColorInformationAdapter extends ArrayAdapter<HSVColor>{
    public HSVColorInformationAdapter(Context context, List<HSVColor> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder; //cache views to avoid future lookup

        //Inflate view if null
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.selected_colors_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.preview = (View) convertView.findViewById(R.id.color_preview);
            viewHolder.name = (TextView) convertView.findViewById(R.id.color_name);
            viewHolder.hsvcode = (TextView) convertView.findViewById(R.id.hsv_value);

            // cache the Views
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate the (sub)views
        HSVColor currentColor = getItem(position);

        viewHolder.preview.setBackgroundColor(currentColor.toInt());
        viewHolder.name.setText(currentColor.getName());
        viewHolder.hsvcode.setText("H: " + currentColor.getHue() + " S: " + currentColor.getSaturation() + " V: " + currentColor.getValue());

        return convertView;
    }

    public static class ViewHolder {
        View preview;
        TextView name;
        TextView hsvcode;
    }
}
