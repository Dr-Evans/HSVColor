package com.example.nick.hsvcolor.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nick.hsvcolor.arrayadapter.HSVColorGradientAdapter;
import com.example.nick.hsvcolor.R;

import java.util.ArrayList;

/**
 * Created by Nick on 4/21/2015.
 */
public class SaturationListFragment extends Fragment {
    private static final double saturationChange = .2;

    private HSVColorGradient hsvColorGradient;
    private ArrayList<HSVColorGradient> mSaturationGradientList;
    private HSVColorGradientAdapter hsvColorGradientAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saturation_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.saturation_list_view);

        if (mSaturationGradientList == null){
            mSaturationGradientList = generateSaturationGradientList();
        }

        hsvColorGradientAdapter = new HSVColorGradientAdapter(getActivity(),
                mSaturationGradientList,
                R.layout.saturation_list_item,
                R.id.saturation_list_item);

        listView.setAdapter(hsvColorGradientAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Open saturation fragment with clicked HSVColor
                ValueListFragment vFragment = new ValueListFragment();

                //Pass clicked HSVColor to new fragment
                //TOGO: ADD BUNDLE SUPPORT
                //Bundle args = new Bundle();
                //args.putParcelable("HSVColor", (Parcelable) parent.getItemAtPosition(position));
                //sFragment.setArguments(args);
                vFragment.setHSVColorGradient((HSVColorGradient) parent.getItemAtPosition(position));

                //Build transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //Replace fragment in activate with new fragment
                transaction.replace(R.id.list_fragment_container, vFragment);

                //Add transaction to the back stack so user can navigate back
                transaction.addToBackStack(null);

                //Commit
                transaction.commit();
            }
        });

        return view;
    }

    private ArrayList<HSVColorGradient> generateSaturationGradientList(){
        ArrayList<HSVColorGradient> satList = new ArrayList<>();

        int numOfInitialColors = (int)Math.round(1.0 / saturationChange);
        for(int i = 0; i < numOfInitialColors; i++){
            //Create clone
            HSVColorGradient gradient = hsvColorGradient.clone();

            HSVColor startColor = gradient.getStartColor();
            HSVColor endColor = gradient.getEndColor();

            //Modify saturation
            startColor.setSaturation(1 - (float)(i * saturationChange));
            endColor.setSaturation(1 - (float)((i + 1) * saturationChange));

            //Add to list
            satList.add(gradient);
        }

        return satList;
    }

    public void setHSVColorGradient(HSVColorGradient hsvColorGradient) {
        this.hsvColorGradient = hsvColorGradient;
    }
}
