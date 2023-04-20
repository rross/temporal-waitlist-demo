package com.rickross.waitlistdemo.utils;

import com.rickross.waitlistdemo.shared.Common;
import com.rickross.waitlistdemo.workflow.SignupDetails;
import com.rickross.waitlistdemo.workflow.WaitlistWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class SignalWorkflow {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("required arguments: emailAddress and either 'I' for invited or 'C' for created'");
            System.exit(1);
        }

        String emailAddress = args[0];
        boolean invited = "i".equals(args[1].toLowerCase());
        boolean created = "c".equals(args[1].toLowerCase());

        if (invited == false && created == false) {
            System.out.println("Missing needed 'I' or 'C'");
            System.exit(2);
        }

        System.out.println("Wanting to send a signal to " + emailAddress + ". Invited is " + invited + " and created is " + created);

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().
                setTaskQueue(Common.WAITLIST_TASK_QUEUE).
                setWorkflowId(emailAddress).build();

        WaitlistWorkflow workflow = client.newWorkflowStub(WaitlistWorkflow.class, emailAddress);

        if (invited) {
            // send signal
            System.out.println("Sending signal to invite user to register");
            workflow.inviteToRegister();
        } else if (created) {
            System.out.println("Sending signal to acknowledge account was created");
            workflow.accountWasCreated();
        }

        System.out.print("The workflow is finished for " + emailAddress);
        System.exit(0);
    }
}
