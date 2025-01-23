package com.example.doctor_patient_portal.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.doctor_patient_portal.Model.Doctor;
import com.example.doctor_patient_portal.Auth.AuthRequest;
import com.example.doctor_patient_portal.Auth.AuthResponse;
import com.example.doctor_patient_portal.Auth.CommonService;
import com.example.doctor_patient_portal.Service.DoctorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping
@CrossOrigin
public class DoctorController {
    @Autowired
    DoctorService service;

    @Autowired
    CommonService service2;

    @PostMapping("/signup/doctor")
    public ResponseEntity<?> doctor(@RequestBody Doctor doctor) {
        try {
            Doctor doc = service.adddoctor(doctor);
            return new ResponseEntity<>(doc, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login/doctor")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return service2.verify(authRequest);
    }

    @GetMapping("/show/doctors")
    public ResponseEntity<List<Doctor>> doctor() {
        return new ResponseEntity<>(service.showdoctor(), HttpStatus.OK);
    }

    @PutMapping("/update/doctor")
    public ResponseEntity<?> updatedoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(service.updateDoctor(doctor), HttpStatus.ACCEPTED);
    }
}