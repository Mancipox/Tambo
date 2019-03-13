package com.tambo.Controller;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String LoginRequest(String reqUrl,String param) {
        String response = null;
        String result =null;
        try {
            URL url = new URL(reqUrl+"?option=byEmail&"+param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set methods and timeouts
            conn.setRequestMethod("GET");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            //connect to URL
            conn.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(conn.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((response = reader.readLine()) != null){
                stringBuilder.append(response);
            }
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return result;
    }
    public String QuestionInfo(String reqUrl, String param) {
        String response = null;
        String result =null;
        try {
            URL url = new URL(reqUrl+"?option=all&"+param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set methods and timeouts
            conn.setRequestMethod("PUT");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            //connect to URL
            conn.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(conn.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((response = reader.readLine()) != null){
                stringBuilder.append(response);
            }
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return null;
    }

    public String SigninRequest(String reqUrl,String param) {
        String response = null;
        String result =null;
        try {
            URL url = new URL(reqUrl+param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set methods and timeouts
            conn.setRequestMethod("PUT");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            //connect to URL
            conn.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(conn.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((response = reader.readLine()) != null){
                stringBuilder.append(response);
            }
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return null;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
