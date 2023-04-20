package com.rickross.waitlistdemo.repository;

import com.rickross.waitlistdemo.entity.WaitlistUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitlistUserRepository extends JpaRepository<WaitlistUser, String> {
}
