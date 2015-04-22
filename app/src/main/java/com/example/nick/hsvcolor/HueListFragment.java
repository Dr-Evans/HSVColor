package com.example.nick.hsvcolor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nick on 4/21/2015.
 */
public class HueListFragment extends Fragment {
    private static final int degrees = 30;
    private static final int hueListItemLayout = R.layout.hue_list_item;
    private static final int hueListItem = R.id.hue_list_item;

    private ArrayList<HSVColor> mHueList;
    private HSVColorAdapter mHSVColorAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hue_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.hue_list_view);

        if (mHueList == null){
            mHueList = generateHueList();
        }

        mHSVColorAdapter = new HSVColorAdapter(getActivity(),
                                               mHueList,
                                               hueListItemLayout,
                                               hueListItem);

        listView.setAdapter(mHSVColorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Open saturation fragment with clicked HSVColor
            }
        });

        return view;
    }

    private ArrayList<HSVColor> generateHueList(){
        ArrayList<HSVColor> hueList = new ArrayList<>();

        int offset = (degrees / 2) * -1;
        int numOfInitialColors = 360 / degrees;
        float mult = 360 / numOfInitialColors;

        for(int i = 0; i < numOfInitialColors; i++){
            float hue = ((i * mult) + offset) % 360;
            hueList.add(new HSVColor(hue, 1, 1));
        }

        return hueList;
    }
}
