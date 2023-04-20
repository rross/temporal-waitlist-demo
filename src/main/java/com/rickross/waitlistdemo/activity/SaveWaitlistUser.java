package com.rickross.waitlistdemo.activity;

import com.rickross.waitlistdemo.entity.WaitlistUser;
import com.rickross.waitlistdemo.workflow.SignupDetails;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface SaveWaitlistUser {
    @ActivityMethod
    WaitlistUser addUserToList(SignupDetails signupDetails);

    @ActivityMethod
    void save(WaitlistUser wlUser);
}
