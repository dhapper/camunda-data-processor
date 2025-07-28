//package com.example.angular.userform;
//
//import com.example.angular.userform.task.VerifyDataWorker;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/submission")
//public class SubmissionController {
//
//    private final VerifyDataWorker worker;
//
//    public SubmissionController(VerifyDataWorker worker) {
//        this.worker = worker;
//    }
//
//    @GetMapping("/{name}")
//    public Object getSubmissionResult(@PathVariable String name) {
//        Object result = worker.getSubmissionResult(name);
//        if (result == null) {
//            return "No submission result found for: " + name;
//        }
//        return result;
//    }
//}
