/*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
package com.tambo.Utils;

        import android.widget.EditText;

        import java.io.BufferedReader;
        import java.nio.charset.StandardCharsets;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.security.NoSuchProviderException;
        import java.security.SecureRandom;
        import java.util.Random;


        import com.google.gson.ExclusionStrategy;
        import com.google.gson.FieldAttributes;
        import com.google.gson.GsonBuilder;
        import java.text.DateFormat;


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
            public static String getSecurePassword(String passwordToHash)
            {
                String generatedPassword = null;
                try {
                    // Create MessageDigest instance for MD5
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                   /* //Add password bytes to digest
                    md.update(passwordToHash.getBytes());
                    //Get the hash's bytes
                    byte[] bytes = md.digest();*/
                    //This bytes[] has bytes in decimal format;
                    byte[] bgeneratedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
                    //Convert it to hexadecimal format
                  /*  StringBuilder sb = new StringBuilder();
                    for(int i=0; i< bytes.length ;i++)
                    {
                        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    //Get complete hashed password in hex format */
                    generatedPassword = bgeneratedPassword.toString();
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
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


        }