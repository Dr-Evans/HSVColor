package com.example.nick.hsvcolor;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.nick.hsvcolor.db.ColorTable;
import com.example.nick.hsvcolor.fragment.HueListFragment;
import com.example.nick.hsvcolor.fragment.SortOrderDialogFragment;


public class MainActivity extends Activity {
    private static final String CHECKED_ID = "CHECKED_ID";
    private static final String ORDER_BY_ID = "ORDER_BY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HSVColorApplication.checkedRadioButtonID = getPreferences(MODE_PRIVATE).getInt(CHECKED_ID, R.id.hsv_radio_button);
        HSVColorApplication.orderBy = getPreferences(MODE_PRIVATE).getString(ORDER_BY_ID, ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_VALUE + " DESC");

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
        editor.commit();
    }

    public void onSortButtonClick(View v){
        DialogFragment sortDialogFragment = new SortOrderDialogFragment();
        sortDialogFragment.show(getFragmentManager(), "sortOrder");
    }
}
