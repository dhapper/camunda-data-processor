package com.example.angular.userform;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WorkflowController {

    @Autowired
    private ZeebeClient zeebeClient;

    private Long currentInstanceKey = null; // store latest process instance key

    // Start a workflow with processId variable
    @PostMapping("/start-workflow")
    public ResponseEntity<Map<String, Object>> startWorkflow(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String processId = (String) ((Map<String, Object>) payload.get("variables")).get("processId");

            long instanceKey = zeebeClient.newCreateInstanceCommand()
                    .bpmnProcessId("Process_verify_data")
                    .latestVersion()
                    .variables(Map.of("processId", processId))
                    .send()
                    .join()
                    .getProcessInstanceKey();

            this.currentInstanceKey = instanceKey;

            response.put("message", "Workflow started");
            response.put("processInstanceId", instanceKey);
            response.put("processId", processId); // Optional, but could help debug

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Failed to start workflow: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Cancel the latest workflow
    @PostMapping("/cancel-workflow")
    public ResponseEntity<String> cancelWorkflow() {
        if (this.currentInstanceKey == null) {
            return ResponseEntity.badRequest().body("No active workflow to cancel");
        }

        try {
            zeebeClient.newCancelInstanceCommand(currentInstanceKey)
                    .send()
                    .join();

            this.currentInstanceKey = null; // reset after cancel
            return ResponseEntity.ok("Workflow canceled successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to cancel workflow: " + e.getMessage());
        }
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody MessagePayload payload) {
        // Debugging: log the message details
        System.out.println("Preparing to send message:");
        System.out.println("Message Name: " + payload.getMessageName());
        System.out.println("Correlation Key: " + payload.getCorrelationKey());
        System.out.println("Variables: " + payload.getVariables());

        try {
            // Log the variables map for detailed inspections
            if (payload.getVariables() != null) {
                payload.getVariables().forEach((key, value) ->
                        System.out.println("Variable: " + key + " = " + value)
                );
            }

            zeebeClient.newPublishMessageCommand()
                    .messageName(payload.getMessageName())
                    .correlationKey(payload.getCorrelationKey())
                    .variables(payload.getVariables())
                    .send()
                    .join();

            System.out.println("Message successfully published");
            return ResponseEntity.ok("Message published");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error publishing message: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to publish message: " + e.getMessage());
        }
    }

}