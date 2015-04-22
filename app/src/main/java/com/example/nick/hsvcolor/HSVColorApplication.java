package com.example.nick.hsvcolor;

import android.app.Application;
import android.content.ContentValues;

import com.example.nick.hsvcolor.db.ColorContentProvider;
import com.example.nick.hsvcolor.db.ColorTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Nick on 4/22/2015.
 */
public class HSVColorApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        ColorFetchThread thread = new ColorFetchThread();
        thread.start();
    }

    private class ColorFetchThread extends Thread {
        String[] urls = {"https://en.wikipedia.org/wiki/List_of_colors:_A%E2%80%93F",
                         "https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M",
                         "http://en.wikipedia.org/wiki/List_of_colors:_N%E2%80%93Z"};

        @Override
        public void run(){
            deleteDatabase(ColorTable.COLOR_TABLE);

            for (int i = 0; i < urls.length; i++) {
                try {
                    Document doc = Jsoup.connect(urls[i]).get();

                    Element body = doc.body();
                    Elements colorRows = body.getElementsByTag("table").first().getElementsByTag("tbody").first().getElementsByTag("tr");

                    boolean first = true;
                    for(Element colorRow : colorRows){
                        //First row is some junk stuff, lets skip that
                        if (first) {
                            first = false;
                            continue;
                        }

                        String name = colorRow.select("th").text();

                        Elements colorHTMLValues = colorRow.select("td");
                        float hue = parseHue(colorHTMLValues.get(4).text());
                        float saturation = parseSaturation(colorHTMLValues.get(7).text());
                        float value = parseValue(colorHTMLValues.get(8).text());

                        ContentValues values = new ContentValues();
                        values.put(ColorTable.COLUMN_NAME, name);
                        values.put(ColorTable.COLUMN_HUE, hue);
                        values.put(ColorTable.COLUMN_SATURATION, saturation);
                        values.put(ColorTable.COLUMN_VALUE, value);

                        getContentResolver().insert(ColorContentProvider.CONTENT_URI, values);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            System.out.println("LOADING FROM ONLINE COMPLETE!");
        }

        private float parseHue(String hueStringWithJunk){
            String[] hueStringArrayWithJunk = hueStringWithJunk.split("♠");
            String hueString = hueStringArrayWithJunk.length == 2 ? hueStringArrayWithJunk[1].substring(0, hueStringArrayWithJunk[1].length() - 1) : "0";

            return Float.parseFloat(hueString);
        }

        private float parseSaturation(String saturationStringWithPercent){
            String saturationString = saturationStringWithPercent.substring(0, saturationStringWithPercent.length() - 1);

            return Float.parseFloat(saturationString) / (float)100;
        }

        private float parseValue(String valueStringWithPercent){
            String valueString = valueStringWithPercent.substring(0, valueStringWithPercent.length() - 1);

            return Float.parseFloat(valueString) / (float)100;
        }
    }
}
