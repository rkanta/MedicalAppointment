package org.example.Kata.service;

import org.example.Kata.model.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAppointmentByPatientId(String patentId);
    void cancelAppointment(Long appointmentId);

    Appointment scheduleAppointmentWithAvailableSlots(Appointment appointment);
}
