package com.rickross.waitlistdemo.service;

import com.rickross.waitlistdemo.entity.WaitlistUser;
import com.rickross.waitlistdemo.repository.WaitlistUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaitlistServiceImpl implements WaitlistService {

    private final WaitlistUserRepository waitlistUserRepository;

    @Autowired
    WaitlistServiceImpl(WaitlistUserRepository waitlistUserRepository) {
        this.waitlistUserRepository = waitlistUserRepository;
    }
    @Override
    public WaitlistUser addToWaitList(String email, String firstName, String lastName) {
        WaitlistUser wlUser = new WaitlistUser(email, firstName, lastName);
        waitlistUserRepository.save(wlUser);
        return wlUser;
    }

    @Override
    public void save(WaitlistUser wlUser) {
        waitlistUserRepository.save(wlUser);
    }
}
