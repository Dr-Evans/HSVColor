package com.example.nick.hsvcolor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nick on 4/21/2015.
 */
public class ValueListFragment extends Fragment {
    private static final double valueChange = .1;

    private HSVColor hsvcolor;
    private ArrayList<HSVColor> mValueList;
    private HSVColorAdapter mHSVColorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.value_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.value_list_view);

        if (mValueList == null){
            mValueList = generateValueList();
        }

        mHSVColorAdapter = new HSVColorAdapter(getActivity(),
                mValueList,
                R.layout.value_list_item,
                R.id.value_list_item);

        listView.setAdapter(mHSVColorAdapter);

        return view;
    }

    private ArrayList<HSVColor> generateValueList(){
        ArrayList<HSVColor> satList = new ArrayList<>();

        int numOfInitialColors = (int)Math.round(1.0 / valueChange);
        for(int i = 0; i < numOfInitialColors; i++){
            //Create clone
            HSVColor hsv = hsvcolor.clone();

            //Modify value
            hsv.setValue(hsv.getValue() - (float)(i * valueChange));

            //Add to list
            satList.add(hsv);
        }

        return satList;
    }

    public void setHSVColor(HSVColor hsvcolor) {
        this.hsvcolor = hsvcolor;
    }
}
