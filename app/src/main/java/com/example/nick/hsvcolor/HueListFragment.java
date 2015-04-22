package com.example.nick.hsvcolor;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
                                               R.layout.hue_list_item,
                                               R.id.hue_list_item);

        listView.setAdapter(mHSVColorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Open saturation fragment with clicked HSVColor
                SaturationListFragment sFragment = new SaturationListFragment();

                //Pass clicked HSVColor to new fragment
                //TOGO: ADD BUNDLE SUPPORT
                //Bundle args = new Bundle();
                //args.putParcelable("HSVColor", (Parcelable) parent.getItemAtPosition(position));
                //sFragment.setArguments(args);
                sFragment.setHSVColor((HSVColor) parent.getItemAtPosition(position));

                //Build transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //Replace fragment in activate with new fragment
                transaction.replace(R.id.list_fragment_container, sFragment);

                //Add transaction to the back stack so user can navigate back
                transaction.addToBackStack(null);

                //Commit
                transaction.commit();
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
