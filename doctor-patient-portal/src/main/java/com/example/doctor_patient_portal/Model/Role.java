package com.example.doctor_patient_portal.Model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    // Admin has full permissions (read, write, update, delete, create) for all
    // actions
    ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_WRITE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_DELETE,
            Permission.MANAGER_READ,
            Permission.MANAGER_WRITE,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_CREATE,
            Permission.MANAGER_DELETE)),

    DOCTOR(Set.of(
            Permission.DOCTOR_READ,
            Permission.DOCTOR_WRITE,
            Permission.DOCTOR_UPDATE)),

    PATIENT(Set.of(
            Permission.PATIENT_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
    
    public static Role fromString(String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return ADMIN;
            case "DOCTOR":
                return DOCTOR;
            case "PATIENT":
                return PATIENT;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
