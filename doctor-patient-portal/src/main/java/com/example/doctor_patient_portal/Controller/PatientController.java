package com.example.doctor_patient_portal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.doctor_patient_portal.Model.Patient;
import com.example.doctor_patient_portal.Service.CommonService;
import com.example.doctor_patient_portal.Service.PatientService;

@RestController
@RequestMapping
@CrossOrigin
public class PatientController {
    @Autowired
    PatientService service;

    @Autowired
    CommonService service2;

    @GetMapping("/patient")
    public ResponseEntity<?> patient() {
        return new ResponseEntity<>(service.showpatient(), HttpStatus.OK);
    }

    @PostMapping("/login/patient")
    public String login(@RequestBody Patient patient) {
        return service2.verify(patient);
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> patient(@RequestBody Patient patient) throws Exception {
        try {
            Patient pat = service.addpatient(patient);
            return new ResponseEntity<>(pat, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
