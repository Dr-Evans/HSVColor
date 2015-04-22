package com.example.nick.hsvcolor;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nick on 4/21/2015.
 */
public class SaturationListFragment extends Fragment {
    private static final double saturationChange = .1;

    private HSVColor hsvcolor;
    private ArrayList<HSVColor> mSaturationList;
    private HSVColorAdapter mHSVColorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saturation_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.saturation_list_view);

        if (mSaturationList == null){
            mSaturationList = generateSaturationList();
        }

        mHSVColorAdapter = new HSVColorAdapter(getActivity(),
                mSaturationList,
                R.layout.saturation_list_item,
                R.id.saturation_list_item);

        listView.setAdapter(mHSVColorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("SaturationListFragment", "Position " + parent.getItemAtPosition(position) + " pressed!");
            }
        });

        return view;
    }

    private ArrayList<HSVColor> generateSaturationList(){
        ArrayList<HSVColor> satList = new ArrayList<>();

        int numOfInitialColors = (int)Math.round(1.0 / saturationChange);
        for(int i = 0; i < numOfInitialColors; i++){
            //Create clone
            HSVColor hsv = hsvcolor.clone();

            //Modify saturation
            hsv.setSaturation(hsv.getSaturation() - (float)(i * saturationChange));

            //Add to list
            satList.add(hsv);
        }

        return satList;
    }

    public void setHSVColor(HSVColor hsvcolor) {
        this.hsvcolor = hsvcolor;
    }
}
