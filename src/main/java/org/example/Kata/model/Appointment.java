package org.example.Kata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    @NotBlank(message = "Patient ID is required.")
    private String patientId;
    @NotBlank(message = "Provider ID is required.")
    private String providerId;
    @NotNull(message = "Date and time are required.")
    @FutureOrPresent(message = "Date and time must be in the future or present.")
    private LocalDateTime dateTime;
    @NotBlank(message = "Status is required")
    private String status;
}
