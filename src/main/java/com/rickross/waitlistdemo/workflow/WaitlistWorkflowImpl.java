package com.rickross.waitlistdemo.workflow;

import com.rickross.waitlistdemo.activity.EmailUser;
import com.rickross.waitlistdemo.activity.SaveWaitlistUser;
import com.rickross.waitlistdemo.entity.WaitlistUser;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class WaitlistWorkflowImpl implements WaitlistWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder().
            setScheduleToCloseTimeout(Duration.ofSeconds(2)).build();

    private final SaveWaitlistUser saveWaitlistUser = Workflow.newActivityStub(SaveWaitlistUser.class, options);
    private final EmailUser emailUser = Workflow.newActivityStub(EmailUser.class, options);

    private boolean canRegister = false;
    private boolean hasAnAccount = false;

    @Override
    public WaitlistUser startWorkflow(SignupDetails signupDetails) {
        Logger logger = Workflow.getLogger(WaitlistWorkflowImpl.class);
        logger.info("Starting workflow for " + signupDetails);

        // Save the user to the database
        WaitlistUser wlUser = saveWaitlistUser.addUserToList(signupDetails);
        logger.info("Saved user to waitlist " + wlUser);

        // Send an email confirmation to the user
        emailUser.emailUser(wlUser.getEmail(), "You're on the waitlist!", "Congratulations! You are on the waitlist!");
        logger.info("On the waitlist email sent to " + wlUser);

        // Wait for a signal to remove them from waitlist
        while (canRegister == false) {
            Workflow.await(() -> canRegister == true);
        }
        logger.info("the user is able to register an account." + wlUser);

        // Send an email inviting them to register
        emailUser.emailUser(wlUser.getEmail(), "You're off the waitlist!", "You can now create your own account here!");

        logger.info("Waiting for the user to create their account");
        // Wait for signal that account has been created
        while (hasAnAccount == false) {
            Workflow.await(() -> hasAnAccount == true);
        }

        // Mark the person as signed up and save it
        wlUser.setInvited(true);
        saveWaitlistUser.save(wlUser);

        return null;
    }

    @Override
    public void inviteToRegister() {
        this.canRegister = true;
    }

    @Override
    public void accountWasCreated() {
        this.hasAnAccount = true;
    }
}
