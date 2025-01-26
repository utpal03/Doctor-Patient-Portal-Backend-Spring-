package com.example.doctor_patient_portal.Model.Patient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodGroup {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String displayName;

    BloodGroup(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static BloodGroup fromDisplayName(String displayName) {
        for (BloodGroup bloodGroup : values()) {
            if (bloodGroup.getDisplayName().equals(displayName)) {
                return bloodGroup;
            }
        }
        throw new IllegalArgumentException("Unknown blood group: " + displayName);
    }

    @JsonValue
    public String toValue() {
        return displayName;
    }
}
