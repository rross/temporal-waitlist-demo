package com.rickross.waitlistdemo.workflow;

import com.rickross.waitlistdemo.entity.WaitlistUser;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WaitlistWorkflow {
    @WorkflowMethod
    WaitlistUser startWorkflow(SignupDetails signupDetails);

    @SignalMethod
    void inviteToRegister();

    @SignalMethod
    void accountWasCreated();
}
