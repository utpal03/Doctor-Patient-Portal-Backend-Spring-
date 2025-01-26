package com.example.doctor_patient_portal.Controller.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.doctor_patient_portal.Auth.AuthRequest;
import com.example.doctor_patient_portal.Auth.AuthResponse;
import com.example.doctor_patient_portal.Auth.CommonService;
import com.example.doctor_patient_portal.Model.Patient.Patient;
import com.example.doctor_patient_portal.Service.PatientService;

@RestController
@RequestMapping
@CrossOrigin
public class PatientController {
    @Autowired
    PatientService service;

    @Autowired
    CommonService service2;

    @GetMapping("/patientInfo")
    public ResponseEntity<?> patient() {
        return new ResponseEntity<>(service.showpatient(), HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> viewAppointments() {
        return new ResponseEntity<>(service.viewAppointments(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/medications")
    public ResponseEntity<?> viewMedications() {
        return new ResponseEntity<>(service.viewMedications(), HttpStatus.ACCEPTED);

    }

    @GetMapping("/reports")
    public ResponseEntity<?> viewReports() {
        return new ResponseEntity<>(service.viewReports(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login/patient")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return service2.verify(authRequest);
    }

    @PostMapping(value = "/signup/patient", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> patient(@ModelAttribute Patient patient,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage)
            throws Exception {
        try {
            Patient savedPatient = service.addpatient(patient, profileImage);
            return new ResponseEntity<>(savedPatient, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
