package com.example.nick.hsvcolor.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nick.hsvcolor.R;
import com.example.nick.hsvcolor.arrayadapter.HSVColorInformationAdapter;
import com.example.nick.hsvcolor.db.ColorContentProvider;
import com.example.nick.hsvcolor.db.ColorTable;

import java.util.ArrayList;

/**
 * Created by Nick on 4/22/2015.
 */
public class SelectedColorsListFragment extends Fragment {
    private static final String[] mProjection = {ColorTable.COLUMN_ID, ColorTable.COLUMN_NAME, ColorTable.COLUMN_HUE, ColorTable.COLUMN_SATURATION, ColorTable.COLUMN_VALUE};
    private static final String mSelectionClause = "WHERE " + ColorTable.COLUMN_HUE + ">=? AND " + ColorTable.COLUMN_HUE + "<=? AND " +
                                                              ColorTable.COLUMN_SATURATION + ">=? AND " + ColorTable.COLUMN_SATURATION + "<=? AND " +
                                                              ColorTable.COLUMN_VALUE + ">=? AND " + ColorTable.COLUMN_VALUE + "<=?";
    private static final String mSortOrder = ColorTable.COLUMN_HUE;
    private Cursor mCursor;
    private HSVColorInformationAdapter hsvColorInformationAdapter;
    private ArrayList<HSVColor> mColorList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_colors_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.selected_colors_list_view);

        //Get selections
        ContentValues values = new ContentValues();
        values.put(ColorTable.COLUMN_NAME, "BLUE");
        values.put(ColorTable.COLUMN_HUE, 250);
        values.put(ColorTable.COLUMN_SATURATION, .3);
        values.put(ColorTable.COLUMN_VALUE, .94);

        ColorContentProvider ccp = new ColorContentProvider();
        ccp.performInsert(values);

        String[] selections = {"200", "300",
                               ".2" , ".6",
                               ".8" , "1"};

        //Perform database query
        mCursor = getActivity().getContentResolver().query(
                ColorContentProvider.CONTENT_URI,
                mProjection,
                mSelectionClause,
                selections,
                mSortOrder
        );

        if(mCursor.getCount() < 1){
            //Nothing found
            Toast.makeText(getActivity(), "NOTHING FOUND", Toast.LENGTH_SHORT).show();
        }
        else {
            //Something found
            //Extract results
            mColorList = getColorList(mCursor);

            //Display results
            hsvColorInformationAdapter = new HSVColorInformationAdapter(getActivity(), mColorList);

            listView.setAdapter(hsvColorInformationAdapter);
        }

        return view;
    }

    private ArrayList<HSVColor> getColorList(Cursor cursor){
        ArrayList<HSVColor> list = new ArrayList<>();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            //Extract data
            String name = cursor.getString(1);
            float hue = cursor.getFloat(2);
            float saturation = cursor.getFloat(3);
            float value = cursor.getFloat(4);

            //Add that shit to the list yo
            list.add(new HSVColor(name, hue, saturation, value));

            //Move that cursor
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }
}
