package com.example.nick.hsvcolor.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nick.hsvcolor.R;
import com.example.nick.hsvcolor.arrayadapter.HSVColorGradientAdapter;

import java.util.ArrayList;

/**
 * Created by Nick on 4/21/2015.
 */
public class HueListFragment extends Fragment {
    private static final int degrees = 90;

    private ArrayList<HSVColorGradient> mHueGradientList;
    private HSVColorGradientAdapter hsvColorGradientAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hue_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.hue_list_view);

        if (mHueGradientList == null){
            mHueGradientList = generateHueGradientList();
        }

        hsvColorGradientAdapter = new HSVColorGradientAdapter(getActivity(),
                                               mHueGradientList,
                                               R.layout.hue_list_item,
                                               R.id.hue_list_item);

        listView.setAdapter(hsvColorGradientAdapter);

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
                sFragment.setHSVColorGradient((HSVColorGradient) parent.getItemAtPosition(position));

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

    private ArrayList<HSVColorGradient> generateHueGradientList(){
        ArrayList<HSVColorGradient> hueList = new ArrayList<>();

        int offset = (degrees / 2) * -1;
        int numOfInitialColors = 360 / degrees;
        float mult = 360 / numOfInitialColors;

        for(int i = 0; i < numOfInitialColors; i++){
            float startHue = ((i * mult) + offset) % 360;
            HSVColor startColor = new HSVColor(startHue, 1, 1);

            float endHue = (((i + 1) * mult) + offset) % 360;
            HSVColor endColor = new HSVColor(endHue, 1, 1);
            hueList.add(new HSVColorGradient(startColor, endColor));
        }

        return hueList;
    }
}
