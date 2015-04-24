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
 * Created by Nick on 4/23/2015.
 */
public class HueColorSwatchDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View hueSwatchView = inflater.inflate(R.layout.hue_swatch_view, null);

        final SeekBar hueSwatchDegreeSeekBar = (SeekBar) hueSwatchView.findViewById(R.id.hue_swatch_degree_seekbar);
        final TextView hueSwatchDegreeTextView = (TextView) hueSwatchView.findViewById(R.id.hue_swatch_degree_text_view);
        final View huePreviewView = hueSwatchView.findViewById(R.id.hue_preview_swatch);

        hueSwatchDegreeSeekBar.setProgress(HueListFragment.centerDegreeOfFirst);
        hueSwatchDegreeTextView.setText(String.valueOf(HueListFragment.centerDegreeOfFirst) + "°");
        hueSwatchDegreeSeekBar.incrementProgressBy(1);
        hueSwatchDegreeSeekBar.setMax(360);

        final HSVColor previewColor = new HSVColor((float) HueListFragment.centerDegreeOfFirst, (float) 1, (float) 1);
        huePreviewView.setBackgroundColor(previewColor.toInt());

        final int currentDegree = HueListFragment.centerDegreeOfFirst;
        hueSwatchDegreeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hueSwatchDegreeTextView.setText(String.valueOf(progress) + "°");
                HueListFragment.centerDegreeOfFirst = progress;

                previewColor.setHue((float) progress);
                huePreviewView.setBackgroundColor(previewColor.toInt());
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

        final SeekBar hueSwatchNumSeekBar = (SeekBar) hueSwatchView.findViewById(R.id.hue_swatch_num_seekbar);
        final TextView hueSwatchNumTextView = (TextView) hueSwatchView.findViewById(R.id.hue_swatch_num_text_view);

        hueSwatchNumSeekBar.setProgress(HueListFragment.numOfSwatches);
        hueSwatchNumTextView.setText(String.valueOf(HueListFragment.numOfSwatches));
        hueSwatchNumSeekBar.incrementProgressBy(1);
        hueSwatchNumSeekBar.setMax(36);

        final int currentNumOfSwatches = HueListFragment.numOfSwatches;
        hueSwatchNumSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress == 0 ? 1 : progress;
                hueSwatchNumTextView.setText(String.valueOf(progress));
                HueListFragment.numOfSwatches = progress;
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
                .setView(hueSwatchView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HueListFragment hueListFragment = (HueListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);
                        hueListFragment.refresh();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HueListFragment.numOfSwatches = currentNumOfSwatches;
                        HueListFragment.centerDegreeOfFirst = currentDegree;
                    }
                });

        return builder.create();
    }
}
