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
public class ValueListFragment extends Fragment {
    private static final double valueChange = .1;

    private HSVColorGradient hsvColorGradient;
    private ArrayList<HSVColorGradient> mValueGradientList;
    private HSVColorGradientAdapter hsvColorGradientAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.value_list_view, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.value_list_view);

        if (mValueGradientList == null){
            mValueGradientList = generateValueGradientList();
        }

        hsvColorGradientAdapter = new HSVColorGradientAdapter(getActivity(),
                mValueGradientList,
                R.layout.value_list_item,
                R.id.value_list_item);

        listView.setAdapter(hsvColorGradientAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Open saturation fragment with clicked HSVColor
                SelectedColorsListFragment scFragment = new SelectedColorsListFragment();

                //Pass clicked HSVColor to new fragment
                //TOGO: ADD BUNDLE SUPPORT
                //Bundle args = new Bundle();
                //args.putParcelable("HSVColor", (Parcelable) parent.getItemAtPosition(position));
                //sFragment.setArguments(args);

                //Build transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //Replace fragment in activate with new fragment
                transaction.replace(R.id.list_fragment_container, scFragment);

                //Add transaction to the back stack so user can navigate back
                transaction.addToBackStack(null);

                //Commit
                transaction.commit();
            }
        });

        return view;
    }

    private ArrayList<HSVColorGradient> generateValueGradientList(){
        ArrayList<HSVColorGradient> satList = new ArrayList<>();

        int numOfInitialColors = (int)Math.round(1.0 / valueChange);
        for(int i = 0; i < numOfInitialColors; i++){
            //Create clone
            HSVColorGradient gradient = hsvColorGradient.clone();

            //Modify value
            gradient.setValue(1 - (float) (i * valueChange));

            //Add to list
            satList.add(gradient);
        }

        return satList;
    }

    public void setHSVColorGradient(HSVColorGradient hsvColorGradient) {
        this.hsvColorGradient = hsvColorGradient;
    }
}
