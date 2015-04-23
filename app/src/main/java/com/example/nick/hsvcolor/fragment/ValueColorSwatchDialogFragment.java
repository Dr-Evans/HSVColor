package com.example.nick.hsvcolor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nick.hsvcolor.R;

/**
 * Created by Nick on 4/22/2015.
 */
public class ValueColorSwatchDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View saturationSwatchView = inflater.inflate(R.layout.value_swatch_view, null);
        final SeekBar valueSwatchSeekBar = (SeekBar) saturationSwatchView.findViewById(R.id.value_swatch_seekbar);
        final TextView valueSwatchTextView = (TextView) saturationSwatchView.findViewById(R.id.value_swatch_text_view);

        valueSwatchSeekBar.setProgress(ValueListFragment.numOfSwatches);
        valueSwatchTextView.setText(String.valueOf(ValueListFragment.numOfSwatches));
        valueSwatchSeekBar.incrementProgressBy(1);
        valueSwatchSeekBar.setMax(256);

        final int currentNumOfSwatches = ValueListFragment.numOfSwatches;
        valueSwatchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress == 0 ? 1 : progress;
                valueSwatchTextView.setText(String.valueOf(progress));
                ValueListFragment.numOfSwatches = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //blank
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //blank
            }
        });

        builder.setTitle("Configure Color Swatches")
                .setView(saturationSwatchView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ValueListFragment valListFragment = (ValueListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);
                        valListFragment.refresh();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ValueListFragment.numOfSwatches = currentNumOfSwatches;
                    }
                });

        return builder.create();
    }
}
