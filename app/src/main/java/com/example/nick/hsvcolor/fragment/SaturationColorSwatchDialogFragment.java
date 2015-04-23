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
public class SaturationColorSwatchDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View saturationSwatchView = inflater.inflate(R.layout.saturation_swatch_view, null);
        final SeekBar saturationSwatchSeekBar = (SeekBar) saturationSwatchView.findViewById(R.id.saturation_swatch_seekbar);
        final TextView saturationSwatchTextView = (TextView) saturationSwatchView.findViewById(R.id.saturation_swatch_text_view);

        saturationSwatchSeekBar.setProgress(SaturationListFragment.numOfSwatches);
        saturationSwatchTextView.setText(String.valueOf(SaturationListFragment.numOfSwatches));
        saturationSwatchSeekBar.incrementProgressBy(1);
        saturationSwatchSeekBar.setMax(256);

        final int currentNumOfSwatches = SaturationListFragment.numOfSwatches;
        saturationSwatchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress == 0 ? 1 : progress;
                saturationSwatchTextView.setText(String.valueOf(progress));
                SaturationListFragment.numOfSwatches = progress;
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
                        SaturationListFragment satListFragment = (SaturationListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);
                        satListFragment.refresh();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaturationListFragment.numOfSwatches = currentNumOfSwatches;
                    }
                });

        return builder.create();
    }
}
