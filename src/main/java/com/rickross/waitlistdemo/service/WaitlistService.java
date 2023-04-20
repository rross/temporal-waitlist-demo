package com.rickross.waitlistdemo.service;

import com.rickross.waitlistdemo.entity.WaitlistUser;

public interface WaitlistService {
    WaitlistUser addToWaitList(String email, String firstName, String lastName);

    void save(WaitlistUser wlUser);
}
