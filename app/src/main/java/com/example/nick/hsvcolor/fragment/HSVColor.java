package com.example.nick.hsvcolor.fragment;

import android.graphics.Color;

/**
 * Created by Nick on 4/21/2015.
 */
public class HSVColor implements Cloneable{
    private float hue;
    private float saturation;
    private float value;
    private String name;

    public HSVColor (float h, float s, float v){
        hue = h;
        saturation = s;
        value = v;
    }

    public HSVColor (String n, float h, float s, float v){
        name = n;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
