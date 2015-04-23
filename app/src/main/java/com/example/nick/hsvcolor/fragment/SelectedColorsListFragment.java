package com.example.nick.hsvcolor.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nick.hsvcolor.HSVColorApplication;
import com.example.nick.hsvcolor.R;
import com.example.nick.hsvcolor.arrayadapter.HSVColorInformationAdapter;
import com.example.nick.hsvcolor.db.ColorContentProvider;
import com.example.nick.hsvcolor.db.ColorTable;

import java.util.ArrayList;

/**
 * Created by Nick on 4/22/2015.
 */
public class SelectedColorsListFragment extends Fragment {
    private static final String mSelectionClause = ColorTable.COLUMN_HUE + ">= ? AND " + ColorTable.COLUMN_HUE + "<= ? AND " +
                                                   ColorTable.COLUMN_SATURATION + ">= ? AND " + ColorTable.COLUMN_SATURATION + "<= ? AND " +
                                                   ColorTable.COLUMN_VALUE + ">= ? AND " + ColorTable.COLUMN_VALUE + "<= ?";
    private HSVColorGradient hsvColorGradient;
    private Cursor mCursor;
    private String[] mSelectionArgs;
    private HSVColorInformationAdapter hsvColorInformationAdapter;
    private ArrayList<HSVColor> mColorList;
    private ListView mListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_colors_list_view, container, false);

        mListView = (ListView) view.findViewById(R.id.selected_colors_list_view);

        //Get selections from color gradient
        float[] args = {
                        hsvColorGradient.getStartColor().getHue(), hsvColorGradient.getEndColor().getHue(),
                        hsvColorGradient.getEndColor().getSaturation(), hsvColorGradient.getStartColor().getSaturation(),
                        hsvColorGradient.getEndColor().getValue(), hsvColorGradient.getStartColor().getValue()
                       };

        mSelectionArgs = new String[args.length];
        for(int i = 0; i < args.length; i++){
            mSelectionArgs[i] = Float.toString(args[i]);
        }

        performDatabaseQuery(mSelectionClause, mSelectionArgs, HSVColorApplication.orderBy);

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

    public void setHSVColorGradient(HSVColorGradient hsvColorGradient) {
        this.hsvColorGradient = hsvColorGradient;
    }

    public void setOrderBy(String orderBy){
        HSVColorApplication.orderBy = orderBy;
        performDatabaseQuery(mSelectionClause, mSelectionArgs, HSVColorApplication.orderBy);
    }

    private void performDatabaseQuery(String selectionClause, String[] args, String orderBy){
        //Perform database query
        mCursor = getActivity().getContentResolver().query(
                ColorContentProvider.CONTENT_URI,
                null,
                selectionClause,
                args,
                orderBy
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

            mListView.setAdapter(hsvColorInformationAdapter);
        }
    }
}
