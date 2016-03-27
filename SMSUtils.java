package com.appdvaya.smsapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
    SMSUtils class contains all methods to manage or search messages in inbox.
 */
public class SMSUtils {

    public static void deleteSMS(Context context, SMMessage message) {
        try {
            Uri uriSms = Uri.parse("content://sms");
            Cursor c = context.getContentResolver().query(uriSms, new String[]{"_id", "thread_id", "address", "person", "date", "body"}, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    String date = c.getString(4);
                    String name = c.getString(3);

                    ContentValues values = new ContentValues();
                    values.put("read", true);
                    context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=" + id, null);

                    if (address.contains(message.getPhoneNumber())) {
                        int rows = context.getContentResolver().delete(Uri.parse("content://sms/" + id), "date=?", new String[]{c.getString(4)});
                    } else {

                    }
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<SMMessage> getMessageList(Context context) {
        ArrayList<SMMessage> messageList = new ArrayList<>();

        try {
            Uri uriSms = Uri.parse("content://sms");
            Cursor c = context.getContentResolver().query(uriSms, new String[]{"_id", "thread_id", "address", "person", "date", "body"}, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    String date = c.getString(4);
                    String name = c.getString(3);
                    ContentValues values = new ContentValues();
                    values.put("read", true);
                    context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=" + id, null);
                    SMMessage smMessage = new SMMessage();
                    smMessage.setPhoneNumber(address);
                    smMessage.setMessage(body);
                    smMessage.setContactName(name);
                    smMessage.setDate(date);
                    messageList.add(smMessage);

                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
    }

    public static boolean getSMSForNumber(Context context, SMQuery smQuery) {
        ArrayList<SMMessage> messageList = getMessageList(context);

        for (int i = 0; i < messageList.size(); i++) {
            if (smQuery.getPhoneNumberToSearch().equals(messageList.get(i).getPhoneNumber())) {
                break;
            } else {
                return false;
            }
        }
        return true;
    }


    public static boolean getSMSForName(Context context, SMQuery smQuery) {
        ArrayList<SMMessage> messageList = getMessageList(context);
        for (int i = 0; i < messageList.size(); i++) {
            if (smQuery.getContactNameToSearch().equals(messageList.get(i).getContactName())) {
                break;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean getSMSForDate(Context context, SMQuery smQuery) {
        ArrayList<SMMessage> messageList = getMessageList(context);
        for (int i = 0; i < messageList.size(); i++) {
            Date date = new Date(messageList.get(i).getDate());
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
            if (smQuery.getContactNameToSearch().equals(formattedDate)) {
                break;
            } else {
                return false;
            }
        }
        return true;
    }
}


