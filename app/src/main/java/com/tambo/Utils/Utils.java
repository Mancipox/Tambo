/*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
package com.tambo.Utils;

        import android.annotation.SuppressLint;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

        import android.content.Context;
        import android.os.Build;
        import android.util.Base64;
        import java.util.Random;

        import at.favre.lib.crypto.bcrypt.BCrypt;


public class Utils {


                /**
                 * FUNCIONES BASICAS
                 **/

                private static Random r = new Random();

                public static int aleatorioEntreDosNumeros(int min, int max) {
                        return (r.nextInt((max - min) + 1) + min);
                }

                public static GsonBuilder builder = null;

                public static String toJson(Object obj) {
                        if (builder == null) {
                                builder = new GsonBuilder();
                        }
                        return builder.setDateFormat("dd/MM/yyyy").create().toJson(obj);
                }


                public static <T> Object fromJson(String json, Class<T> obj) {
                        if (builder == null) {
                                builder = new GsonBuilder();
                                builder.setDateFormat("dd/MM/yyyy");

//			builder.setDateFormat(DateFormat.FULL, DateFormat.FULL);

                                //builder.setExclusionStrategies(new TestExclStrat());
                        }
                        return builder.create().fromJson(json, obj);
                }

                //Method to hash the password using MD5 with salt
            @SuppressLint("NewApi")
            public static String getSecurePassword(String passwordToHash)
            {
                String generatedPassword = null;


                        generatedPassword= BCrypt.withDefaults().hashToString(12,passwordToHash.toCharArray());

                    //Get complete hashed password in hex format */


                return generatedPassword;
            }

            //Add salt
            public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
            {
                //Always use a SecureRandom generator
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                //Create array for salt
                byte[] salt = new byte[16];
                //Get a random salt
                sr.nextBytes(salt);
                //return salt
                return salt;
            }

                public static class TestExclStrat implements ExclusionStrategy {

                        public boolean shouldSkipClass(Class<?> arg0) {
                                return false;
                        }

                        public boolean shouldSkipField(FieldAttributes f) {
                                return false;

                        }

                }


    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
        }