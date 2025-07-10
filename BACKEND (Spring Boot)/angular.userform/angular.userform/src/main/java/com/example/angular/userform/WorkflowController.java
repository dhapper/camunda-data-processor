package com.example.angular.userform;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkflowController {

    @PostMapping("/api/workflow/start")
    public String startWorkflow(@RequestBody UserModel userModel) {
        // You can process the data here
        return "Workflow started for " + userModel.getFirstName() + " " + userModel.getLastName();
    }
}