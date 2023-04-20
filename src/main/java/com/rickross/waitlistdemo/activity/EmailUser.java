package com.rickross.waitlistdemo.activity;


import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface EmailUser {
    @ActivityMethod
    void emailUser(String emailAddress, String subject, String body);
}
