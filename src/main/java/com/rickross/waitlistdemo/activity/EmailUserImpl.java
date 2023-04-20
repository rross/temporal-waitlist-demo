package com.rickross.waitlistdemo.activity;

public class EmailUserImpl implements EmailUser {

    @Override
    public void emailUser(String emailAddress, String subject, String body) {
        // for now we'll just print out the email
        System.out.println("Sending email to " + emailAddress + " with subject of " + subject + " and body of " + body);
    }
}
