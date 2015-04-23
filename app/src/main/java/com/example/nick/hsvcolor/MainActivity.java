package com.example.nick.hsvcolor;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.nick.hsvcolor.db.ColorTable;
import com.example.nick.hsvcolor.fragment.HueColorSwatchDialogFragment;
import com.example.nick.hsvcolor.fragment.HueListFragment;
import com.example.nick.hsvcolor.fragment.SaturationColorSwatchDialogFragment;
import com.example.nick.hsvcolor.fragment.SaturationListFragment;
import com.example.nick.hsvcolor.fragment.SortOrderDialogFragment;
import com.example.nick.hsvcolor.fragment.ValueColorSwatchDialogFragment;
import com.example.nick.hsvcolor.fragment.ValueListFragment;


public class MainActivity extends Activity {
    private static final String HUE_SWATCH_NUM = "HUE_SWATCH_NUM";
    private static final String HUE_DEGREE = "HUE_DEGREE";
    private static final String CHECKED_ID = "CHECKED_ID";
    private static final String ORDER_BY_ID = "ORDER_BY";
    private static final String SATURATION_SWATCH_NUM = "SATURATION_SWATCH_NUM";
    private static final String VALUE_SWATCH_NUM = "VALUE_SWATCH_NUM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HueListFragment.numOfSwatches = getPreferences(MODE_PRIVATE).getInt(HUE_SWATCH_NUM, 10);
        HueListFragment.centerDegreeOfFirst = getPreferences(MODE_PRIVATE).getInt(HUE_DEGREE, 0);
        HSVColorApplication.checkedRadioButtonID = getPreferences(MODE_PRIVATE).getInt(CHECKED_ID, R.id.hsv_radio_button);
        HSVColorApplication.orderBy = getPreferences(MODE_PRIVATE).getString(ORDER_BY_ID, ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_VALUE + " DESC");
        SaturationListFragment.numOfSwatches = getPreferences(MODE_PRIVATE).getInt(SATURATION_SWATCH_NUM, 10);
        ValueListFragment.numOfSwatches = getPreferences(MODE_PRIVATE).getInt(VALUE_SWATCH_NUM, 10);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.list_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HueListFragment hueFragment = new HueListFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.list_fragment_container, hueFragment).commit();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(CHECKED_ID, HSVColorApplication.checkedRadioButtonID);
        editor.putString(ORDER_BY_ID, HSVColorApplication.orderBy);
        editor.putInt(HUE_SWATCH_NUM, HueListFragment.numOfSwatches);
        editor.putInt(HUE_DEGREE, HueListFragment.centerDegreeOfFirst);
        editor.putInt(SATURATION_SWATCH_NUM, SaturationListFragment.numOfSwatches);
        editor.putInt(VALUE_SWATCH_NUM, ValueListFragment.numOfSwatches);
        editor.commit();
    }

    public void onSortButtonClick(View v){
        DialogFragment sortDialogFragment = new SortOrderDialogFragment();
        sortDialogFragment.show(getFragmentManager(), "sortOrder");
    }

    public void onHueSwatchesButtonClick(View v){
        DialogFragment hueDialogFragment = new HueColorSwatchDialogFragment();
        hueDialogFragment.show(getFragmentManager(), "hueSwatches");
    }

    public void onSaturationSwatchesButtonClick(View v){
        DialogFragment saturationDialogFragment = new SaturationColorSwatchDialogFragment();
        saturationDialogFragment.show(getFragmentManager(), "saturationSwatches");
    }

    public void onValueSwatchesButtonClick(View v){
        DialogFragment valueDialogFragment = new ValueColorSwatchDialogFragment();
        valueDialogFragment.show(getFragmentManager(), "valueSwatches");
    }
}
