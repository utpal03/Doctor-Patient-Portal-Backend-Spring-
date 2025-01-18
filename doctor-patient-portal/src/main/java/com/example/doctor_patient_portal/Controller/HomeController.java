package com.example.doctor_patient_portal.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/")
    public String greet(){
        return "hey welcome to my doctor patient portal";
    }
}
