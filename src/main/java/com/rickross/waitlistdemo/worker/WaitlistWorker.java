package com.rickross.waitlistdemo.worker;

import com.rickross.waitlistdemo.activity.EmailUserImpl;
import com.rickross.waitlistdemo.activity.SaveWaitlistUserImpl;
import com.rickross.waitlistdemo.service.WaitlistService;
import com.rickross.waitlistdemo.shared.Common;
import com.rickross.waitlistdemo.workflow.WaitlistWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.rickross"})
public class WaitlistWorker implements CommandLineRunner {

    public WaitlistWorker(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WaitlistWorker.class, args);
    }

    @Autowired
    private final WaitlistService waitlistService;

    @Override
    public void run(String... args) throws Exception {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Common.WAITLIST_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(WaitlistWorkflowImpl.class);
        worker.registerActivitiesImplementations(new EmailUserImpl());
        worker.registerActivitiesImplementations(new SaveWaitlistUserImpl(waitlistService));
        factory.start();
    }
}
