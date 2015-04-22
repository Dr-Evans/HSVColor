package com.example.nick.hsvcolor;

import android.app.Application;

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
            //for (int i = 0; i < urls.length; i++) {
                StringBuffer sb = new StringBuffer();

                try {
                    Document doc = Jsoup.connect(urls[0]).get();

                    Element body = doc.body();
                    Elements colorRows = body.getElementsByTag("table").first().getElementsByTag("tbody").first().getElementsByTag("tr");
                    String colorRowsHtml = colorRows.html();

                    boolean first = true;
                    for(Element colorRow : colorRows){
                        //First row is some junk stuff, lets skip that
                        if (first) {
                            first = false;
                            continue;
                        }
                        String colorRowHtml = colorRow.html();
                        String name = colorRow.select("th").text();

                        Elements values = colorRow.select("td");
                        System.out.println(name);
                        String hue = values.get(4).text();
                        System.out.println(hue);
                        String saturation = values.get(7).text();
                        System.out.println(saturation);
                        String value = values.get(8).text();
                        System.out.println(value);
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            //}
        }
    }
}
