package com.example.doctor_patient_portal.Model;

import java.io.Serializable;

import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {
    private int id;
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id) && Objects.equals(username, userId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

}
