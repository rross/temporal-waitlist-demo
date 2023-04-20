package com.rickross.waitlistdemo.activity;

import com.rickross.waitlistdemo.entity.WaitlistUser;
import com.rickross.waitlistdemo.service.WaitlistService;
import com.rickross.waitlistdemo.workflow.SignupDetails;

public class SaveWaitlistUserImpl implements SaveWaitlistUser {

    private final WaitlistService waitlistService;

    public SaveWaitlistUserImpl(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }
    @Override
    public WaitlistUser addUserToList(SignupDetails signupDetails) {
        WaitlistUser wlUser = waitlistService.addToWaitList(signupDetails.getEmail(),
                signupDetails.getFirstName(),
                signupDetails.getLastName());

        return wlUser;
    }

    @Override
    public void save(WaitlistUser wlUser) {
        waitlistService.save(wlUser);
    }
}
