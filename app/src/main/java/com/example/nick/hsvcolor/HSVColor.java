package com.example.nick.hsvcolor;

import android.graphics.Color;

/**
 * Created by Nick on 4/21/2015.
 */
public class HSVColor implements Cloneable{
    private float hue;
    private float saturation;
    private float value;

    HSVColor (float h, float s, float v){
        hue = h;
        saturation = s;
        value = v;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int toInt(){
        return Color.HSVToColor(toArray());
    }

    private float[] toArray(){
        float[] array = {hue, saturation, value};

        return array;
    }

    @Override
    public HSVColor clone(){
        return new HSVColor(hue, saturation, value);
    }

    @Override
    public String toString(){
        return "Hue: " + hue + ", Saturation: " + saturation + ", Value: " + value;
    }
}
