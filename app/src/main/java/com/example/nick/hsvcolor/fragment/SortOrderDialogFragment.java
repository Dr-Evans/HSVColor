package com.example.nick.hsvcolor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nick.hsvcolor.HSVColorApplication;
import com.example.nick.hsvcolor.R;
import com.example.nick.hsvcolor.db.ColorTable;

/**
 * Created by Nick on 4/22/2015.
 */
public class SortOrderDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View sortView = inflater.inflate(R.layout.sort_view, null);
        RadioButton rb = (RadioButton) sortView.findViewById(HSVColorApplication.checkedRadioButtonID);
        rb.setChecked(true);

        builder.setTitle("CHANGE SORT ORDER")
               .setView(sortView)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User hit ok, change orderBy
                        RadioGroup sortOrderRadioGroup = (RadioGroup) sortView.findViewById(R.id.sort_order_radio_group);

                        SelectedColorsListFragment colorsListFragment = (SelectedColorsListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);

                        int checkedRadioButtonID = sortOrderRadioGroup.getCheckedRadioButtonId();

                        //If the ID is the same, no need to do all this stuff
                        if (checkedRadioButtonID == HSVColorApplication.checkedRadioButtonID){
                            return;
                        }

                        String orderBy;
                        switch (checkedRadioButtonID) {
                            case R.id.hsv_radio_button:
                                orderBy = ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_VALUE + " DESC";
                                break;
                            case R.id.hvs_radio_button:
                                orderBy = ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_VALUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC";
                                break;
                            case R.id.shv_radio_button:
                                orderBy = ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_VALUE + " DESC";
                                break;
                            case R.id.svh_radio_button:
                                orderBy = ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_VALUE + " DESC, " + ColorTable.COLUMN_HUE + " DESC";
                                break;
                            case R.id.vhs_radio_button:
                                orderBy = ColorTable.COLUMN_VALUE + " DESC, " + ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC";
                                break;
                            case R.id.vsh_radio_button:
                                orderBy = ColorTable.COLUMN_VALUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_HUE + " DESC";
                                break;
                            default:
                                orderBy = ColorTable.COLUMN_HUE + " DESC, " + ColorTable.COLUMN_SATURATION + " DESC, " + ColorTable.COLUMN_VALUE + " DESC";
                        }

                        HSVColorApplication.orderBy = orderBy;
                        HSVColorApplication.checkedRadioButtonID = checkedRadioButtonID;
                        colorsListFragment.setOrderBy(orderBy);
                    }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //User canceled, do nothing
                   }
               });

        return builder.create();
    }
}
