package com.example.nick.hsvcolor;

/**
 * Created by Nick on 4/22/2015.
 */
public class HSVColorGradient implements Cloneable{
    private HSVColor startColor;
    private HSVColor endColor;

    HSVColorGradient(HSVColor firstColor, HSVColor lastColor){
        this.startColor = firstColor.clone(); //These clones are important!!
        this.endColor = lastColor.clone();
    }

    public int[] toIntArray(){
        int[] i = {startColor.toInt(), endColor.toInt()};

        return i;
    }

    public HSVColor getEndColor() {
        return endColor;
    }

    public void setEndColor(HSVColor endColor) {
        this.endColor = endColor;
    }

    public HSVColor getStartColor() {
        return startColor;
    }

    public void setStartColor(HSVColor startColor) {
        this.startColor = startColor;
    }

    public void setSaturation(float sat){
        this.startColor.setSaturation(sat);
        this.endColor.setSaturation(sat);
    }

    public void setValue(float val){
        this.startColor.setValue(val);
        this.endColor.setValue(val);
    }

    @Override
    public HSVColorGradient clone(){
        return new HSVColorGradient(startColor, endColor);
    }
}
