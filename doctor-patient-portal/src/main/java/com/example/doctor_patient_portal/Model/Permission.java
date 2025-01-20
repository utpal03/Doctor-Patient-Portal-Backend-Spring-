package com.example.doctor_patient_portal.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    
    MANAGER_READ("management:read"),
    MANAGER_WRITE("management:write"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    
    DOCTOR_READ("doctor:read"),
    DOCTOR_WRITE("doctor:write"),
    DOCTOR_UPDATE("doctor:update"),
    
    PATIENT_READ("patient:read");

    @Getter
    private final String permission;
}
