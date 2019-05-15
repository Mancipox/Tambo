/*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
package com.tambo.Utils;

        import java.io.BufferedReader;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.util.Random;


        import com.google.gson.ExclusionStrategy;
        import com.google.gson.FieldAttributes;
        import com.google.gson.GsonBuilder;
        import java.text.DateFormat;


        public class Utils {


        /** FUNCIONES BASICAS **/

        private static Random r = new Random();

        public static int aleatorioEntreDosNumeros(int min, int max){
        return (r.nextInt((max - min) + 1) + min);
        }

        public static GsonBuilder builder = null;
        public static String toJson(Object obj){
        if(builder == null){
        builder = new GsonBuilder();
        }
        return builder.setDateFormat("dd/MM/yyyy").create().toJson(obj);
        }



        public static <T> Object fromJson(String json, Class<T> obj){
        if(builder == null){
        builder = new GsonBuilder();
        builder.setDateFormat("dd/MM/yyyy");

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

        public static String hashPassword(String password){
                String hpass="";
                try{
                        MessageDigest algorithm = MessageDigest.getInstance("MD5");
                        byte[] defaultBytes = password.getBytes();
                        algorithm.reset();
                        algorithm.update(defaultBytes);
                        byte messageDigest[] = algorithm.digest();

                        StringBuffer hexString = new StringBuffer();
                        for (int i=0;i<messageDigest.length;i++) {
                                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                        }
                        hpass=hexString.toString();
                }catch(NoSuchAlgorithmException nsae){

                }
                return hpass;

        }
        }



        }