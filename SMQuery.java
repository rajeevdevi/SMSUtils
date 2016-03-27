package com.appdvaya.smsapp.myapplication;

public class SMQuery {


    private String message;
    private String phoneNumber;
    private String contactName;
    private String date;

    public String getMessageToSearch() {

        return message;
    }

    public void setMessageToSearch(String message) {

        this.message = message;
    }

    public String getPhoneNumberToSearch() {

        return phoneNumber;
    }


    public void setNumberToSearch(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getContactNameToSearch() {

        return contactName;
    }

    public void setContactNameToSearch(String contactName) {

        this.contactName = contactName;
    }

}
