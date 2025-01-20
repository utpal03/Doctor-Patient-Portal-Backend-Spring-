package com.example.doctor_patient_portal.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.doctor_patient_portal.Model.Admin;
import com.example.doctor_patient_portal.Service.AdminService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.doctor_patient_portal.Auth.AuthResponse;
import com.example.doctor_patient_portal.Auth.CommonService;

@RestController
@CrossOrigin
@RequestMapping
public class AdminController {

    @Autowired
    AdminService service;

    @Autowired
    CommonService service2;

    @PostMapping("/signup/admin")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        try {
            return new ResponseEntity<>(service.register(admin), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/admin")
    public AuthResponse login(@RequestBody Admin admin) {
        return service2.verify(admin);
    }

    @GetMapping("/show/admin")
    public ResponseEntity<List<Admin>> admin() {
        return new ResponseEntity<>(service.showadmin(), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void postMethodName(HttpServletRequest req, HttpServletResponse res) throws StreamWriteException, DatabindException, IOException {
        service2.refreshToken(req, res);
    }

}
