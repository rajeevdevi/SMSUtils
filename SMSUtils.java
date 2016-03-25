package com.appdvaya.smsapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by pradeep on 25/03/16.
 */
public class SMSUtils {
    /*
    SMSUtils class contains all methods to manage or search messages in inbox.
     */

    public static void deleteSMS(Context context, String message, String number)
    {
        try {
            Uri uriSms = Uri.parse("content://sms");
            Cursor c = context.getContentResolver().query(uriSms, new String[] { "_id", "thread_id", "address", "person", "date", "body" }, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    String date = c.getString(3);

                    ContentValues values = new ContentValues();
                    values.put("read", true);
                    context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=" + id, null);

                    if ( address.contains(number)) {
                        int rows = context.getContentResolver().delete(Uri.parse("content://sms/" + id), "date=?",new String[] { c.getString(4) });
                    }
                    else
                    {
                        Log.e("log>>>", "Delete fail........ rows: "+address);
                    }
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("log>>>", e.toString());
            Log.e("log>>>", e.getMessage());
        }
    }
    }

