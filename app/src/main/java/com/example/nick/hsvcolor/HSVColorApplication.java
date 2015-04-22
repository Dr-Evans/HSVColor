package com.example.nick.hsvcolor;

import android.app.Application;

/**
 * Created by Nick on 4/22/2015.
 */
public class HSVColorApplication extends Application {
    private class ColorFetchThread extends Thread {
        String[] urls = {"https://en.wikipedia.org/wiki/List_of_colors:_A%E2%80%93F",
                         "https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M",
                         "http://en.wikipedia.org/wiki/List_of_colors:_N%E2%80%93Z"};

        @Override
        public void run(){
            for (int i = 0; i < urls.length; i++) {
                StringBuffer sb = new StringBuffer();

                try {
                    //Document doc = Jsoup.connect(urls[i]).get();
                }
                catch (Exception e){

                }
            }
        }
    }
}
