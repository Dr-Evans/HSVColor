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
    public static int numOfSwatches;

    private HSVColorGradient hsvColorGradient;
    private ArrayList<HSVColorGradient> mValueGradientList;
    private HSVColorGradientAdapter hsvColorGradientAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.value_list_view, container, false);

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

                scFragment.setHSVColorGradient((HSVColorGradient) parent.getItemAtPosition(position));

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

        double valueChange = 1.0 / (double)numOfSwatches;
        for(int i = 0; i < numOfSwatches; i++){
            //Create clone
            HSVColorGradient gradient = hsvColorGradient.clone();

            HSVColor startColor = gradient.getStartColor();
            HSVColor endColor = gradient.getEndColor();

            //Modify value
            startColor.setValue(1 - (float)(i * valueChange));
            System.out.println(startColor.getValue());
            endColor.setValue(1 - (float)((i + 1) * valueChange));
            System.out.println(endColor.getValue());

            //Add to list
            satList.add(gradient);
        }

        return satList;
    }

    public void setHSVColorGradient(HSVColorGradient hsvColorGradient) {
        this.hsvColorGradient = hsvColorGradient;
    }

    public void refresh(){
        final ListView listView = (ListView) view.findViewById(R.id.value_list_view);

        mValueGradientList = generateValueGradientList();

        hsvColorGradientAdapter = new HSVColorGradientAdapter(getActivity(),
                mValueGradientList,
                R.layout.value_list_item,
                R.id.value_list_item);

        listView.setAdapter(hsvColorGradientAdapter);
    }
}
