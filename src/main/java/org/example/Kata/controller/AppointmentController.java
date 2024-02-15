package org.example.Kata.controller;


import org.example.Kata.model.Appointment;
import org.example.Kata.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> scheduleAppointment(@Valid @RequestBody Appointment appointment){
        Appointment scheduleAppointment = appointmentService.scheduleAppointmentWithAvailableSlots(appointment);
        return ResponseEntity.ok(scheduleAppointment);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        // Localized exception handling
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // HttpStatus.CONFLICT = 409
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatientId(@PathVariable @NotBlank(message = "Patient ID must not be blank") String patientId){
        List<Appointment> appointments = appointmentService.getAppointmentByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable @NotNull(message = "Appointment ID cannot be null") Long appointmentId){
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

}
