//package com.example.angular.userform;
//
//import io.camunda.zeebe.client.ZeebeClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//public class UserFormController {
//
//
//    private final ZeebeClient zeebeClient;
//    private Map<String, Object> data = null;
//
//    public UserFormController(ZeebeClient zeebeClient) {
//        this.zeebeClient = zeebeClient;
//    }
//
//    // GET endpoint
//    @GetMapping("/api/userform")
//    public String getData() {
//        return data != null ? data.get("firstName").toString() : "No data available.";
//    }
//
//    @PostMapping("/api/userform")
//    public String userformEndpoint(@RequestBody Map<String, Object> payload) {
//        System.out.println("Received payload: " + payload);
//        data = payload;
//
//
//        try {
//            zeebeClient.newCreateInstanceCommand()
//                    .bpmnProcessId("Process_verify_data") // should match the process id inside your BPMN
//                    .latestVersion()
//                    .variables(payload)
//                    .send()
//                    .join(); // wait for the command to complete
//            return "Workflow instance started successfully.";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to start workflow: " + e.getMessage();
//        }
//    }
//
//
//
//
//
//
//
//
//}