package com.tambo.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;


public class Utils {

    public static String readParams(HttpServletRequest request) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            request.setCharacterEncoding("UTF-8");
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /* report an error */ }

        line=jb.toString();
        return line;
    }


    /** FUNCIONES BASICAS **/

    private static Random r = new Random();

    public static int aleatorioEntreDosNumeros(int min, int max){
        return (r.nextInt((max - min) + 1) + min);
    }

    public static GsonBuilder builder = null;
    public static String toJson(Object obj){
        if(builder == null){
            builder = new GsonBuilder();
            builder.setExclusionStrategies(new TestExclStrat());
        }
        return builder.create().toJson(obj);
    }



    public static <T> Object fromJson(String json, Class<T> obj){
        if(builder == null){
            builder = new GsonBuilder();
   //         builder.setDateFormat("dd/MM/yyyy");
//			builder.setDateFormat(DateFormat.FULL, DateFormat.FULL);

            //builder.setExclusionStrategies(new TestExclStrat());
        }
        return builder.create().fromJson(json, obj);
    }

    public static class TestExclStrat implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return false;

        }

    }



}