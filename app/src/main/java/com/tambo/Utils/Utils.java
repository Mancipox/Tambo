/*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
package com.tambo.Utils;

        import android.annotation.SuppressLint;
        import android.content.ContentUris;
        import android.content.Context;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Environment;
        import android.provider.DocumentsContract;
        import android.provider.MediaStore;

        import java.net.URISyntaxException;
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

        }

                @SuppressLint("NewApi")
                public static String getPath(Context context, Uri uri) throws URISyntaxException {
                        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
                        String selection = null;
                        String[] selectionArgs = null;
                        // Uri is different in versions after KITKAT (Android 4.4), we need to
                        // deal with different Uris.
                        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
                                if (isExternalStorageDocument(uri)) {
                                        final String docId = DocumentsContract.getDocumentId(uri);
                                        final String[] split = docId.split(":");
                                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                                } else if (isDownloadsDocument(uri)) {
                                        final String id = DocumentsContract.getDocumentId(uri);
                                        uri = ContentUris.withAppendedId(
                                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                                } else if (isMediaDocument(uri)) {
                                        final String docId = DocumentsContract.getDocumentId(uri);
                                        final String[] split = docId.split(":");
                                        final String type = split[0];
                                        if ("image".equals(type)) {
                                                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                        } else if ("video".equals(type)) {
                                                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                        } else if ("audio".equals(type)) {
                                                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                        }
                                        selection = "_id=?";
                                        selectionArgs = new String[]{ split[1] };
                                }
                        }
                        if ("content".equalsIgnoreCase(uri.getScheme())) {
                                String[] projection = { MediaStore.Images.Media.DATA };
                                Cursor cursor = null;
                                try {
                                        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                        if (cursor.moveToFirst()) {
                                                return cursor.getString(column_index);
                                        }
                                } catch (Exception e) {
                                }
                        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                                return uri.getPath();
                        }
                        return null;
                }


                /**
                 * @param uri The Uri to check.
                 * @return Whether the Uri authority is ExternalStorageProvider.
                 */
                public static boolean isExternalStorageDocument(Uri uri) {
                        return "com.android.externalstorage.documents".equals(uri.getAuthority());
                }

                /**
                 * @param uri The Uri to check.
                 * @return Whether the Uri authority is DownloadsProvider.
                 */
                public static boolean isDownloadsDocument(Uri uri) {
                        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
                }

                /**
                 * @param uri The Uri to check.
                 * @return Whether the Uri authority is MediaProvider.
                 */
                public static boolean isMediaDocument(Uri uri) {
                        return "com.android.providers.media.documents".equals(uri.getAuthority());
                }


        }