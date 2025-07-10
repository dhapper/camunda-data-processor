package com.example.angular.userform;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    private final ZeebeClient zeebeClient;

    public TestController(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    private Map<String, Object> data = null;

    // GET endpoint
    @GetMapping("/api/test")
    public String getData() {
        return data != null ? data.get("firstName").toString() : "No data available.";
    }

    @PostMapping("/api/test")
    public String testEndpoint(@RequestBody Map<String, Object> payload) {
        System.out.println("Received test payload: " + payload);
        data = payload;


        try {
            zeebeClient.newCreateInstanceCommand()
                    .bpmnProcessId("Process_verify_data") // should match the process id inside your BPMN
                    .latestVersion()
                    .variables(payload)
                    .send()
                    .join(); // wait for the command to complete
            return "Workflow instance started successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to start workflow: " + e.getMessage();
        }
    }








}