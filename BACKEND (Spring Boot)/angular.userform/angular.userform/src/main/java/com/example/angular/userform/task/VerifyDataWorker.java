//package com.example.angular.userform.task;
//
//import io.camunda.zeebe.client.ZeebeClient;
//import io.camunda.zeebe.client.api.response.ActivatedJob;
//import io.camunda.zeebe.client.api.worker.JobClient;
//import io.camunda.zeebe.client.api.worker.JobWorker;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class VerifyDataWorker {
//
//    private final ZeebeClient zeebeClient;
//    private final Map<String, Object> submissionResults = new ConcurrentHashMap<>();
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public VerifyDataWorker(ZeebeClient zeebeClient) {
//        this.zeebeClient = zeebeClient;
//    }
//
//    @PostConstruct
//    public void startWorker() {
//        zeebeClient.newWorker()
//                .jobType("verify-data") // Must match <zeebe:taskDefinition type="verify-data"/>
//                .handler(this::handleJob)
//                .name("manual-worker")
//                .open(); // Starts the worker thread
//    }
//
//    private void handleJob(JobClient client, ActivatedJob job) {
//        try {
//            Map<String, Object> variables = job.getVariablesAsMap();
//            System.out.println("Manual worker received: " + variables);
//
//            String firstName = (String) variables.get("firstName");
//            String postalCode = (String) variables.get("postalCode"); // assuming postalCode is part of variables
//
//            // Basic validation for firstName
//            boolean isNameValid = firstName != null && !firstName.trim().isEmpty();
//
//            // Postal code verification
//            boolean isPostalCodeValid = false;
//            if (postalCode != null && !postalCode.trim().isEmpty()) {
//                isPostalCodeValid = verifyPostalCode(postalCode);
//            }
//
//            boolean isDataValid = isNameValid && isPostalCodeValid;
//
//            Map<String, Object> result = Map.of(
//                    "isValid", isDataValid,
//                    "nameValid", isNameValid,
//                    "isPostalCodeValid", isPostalCodeValid
////                    "isVerified", true
//            );
//
//            client.newCompleteCommand(job.getKey())
//                    .variables(result)
//                    .send()
//                    .join();
//
//            String prefix = "worker --- ";
//            System.out.println(prefix + "Job completed with result: " + result);
//
//            // Assign an ID
//            Random r = new Random();
//            int id = r.nextInt(100);
//            System.out.println(prefix + firstName + "'s submission assigned id: " + id);
//
//            String key = firstName != null ? firstName.toLowerCase() : "unknown";
//
//            submissionResults.put(key, Map.of(
//                    "isValid", isDataValid,
//                    "nameValid", isNameValid,
//                    "isPostalCodeValid", isPostalCodeValid,
//                    "assignedId", id
////                    "isVerified", true
//            ));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean verifyPostalCode(String postalCode) {
//        // Example using Zippopotam.us API
//        String countryCode = "us"; // Change as needed
//        String sanitizedPostalCode = postalCode.replaceAll("\\s+", "").toUpperCase();
//        String url = String.format("http://api.zippopotam.us/%s/%s", countryCode, sanitizedPostalCode);
//        try {
//            Map response = restTemplate.getForObject(url, Map.class);
//            return response != null && !response.isEmpty();
//        } catch (Exception e) {
//            System.out.println("Postal code verification failed: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public Object getSubmissionResult(String name) {
//        return submissionResults.get(name.toLowerCase());
//    }
//}