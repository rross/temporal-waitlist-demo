package com.rickross.waitlistdemo.utils;

import com.rickross.waitlistdemo.shared.Common;
import com.rickross.waitlistdemo.workflow.SignupDetails;
import com.rickross.waitlistdemo.workflow.WaitlistWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
public class InitiateWorkflow {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("required arguments: emailAddress firstName lastName");
            System.exit(1);
        }
        String email = args[0];
        String firstName = args[1];
        String lastName = args[2];

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().
                setTaskQueue(Common.WAITLIST_TASK_QUEUE).
                setWorkflowId(email).build();
        WaitlistWorkflow workflow = client.newWorkflowStub(WaitlistWorkflow.class, options);

        SignupDetails signupDetails = new SignupDetails(email, firstName, lastName);

        workflow.startWorkflow(signupDetails);
        System.out.print("The workflow is finished for " + signupDetails);
        System.exit(0);
    }
}
