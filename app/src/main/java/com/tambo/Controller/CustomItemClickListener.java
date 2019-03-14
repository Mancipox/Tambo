package com.tambo.Controller;

import android.view.View;

/**
 * Interface to listen a click in specific Item in Tablayout
 */
public interface CustomItemClickListener {
    String url_server = "http://172.25.15.210:8080/TamboServer/";
    void onItemClick(View v, int position);
}
