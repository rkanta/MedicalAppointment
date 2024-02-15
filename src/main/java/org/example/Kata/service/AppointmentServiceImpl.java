package org.example.Kata.service;

import org.example.Kata.model.Appointment;
import org.example.Kata.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{


    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Appointment scheduleAppointmentWithAvailableSlots(Appointment appointment) {
        if(appointment==null){
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        LocalDateTime start = appointment.getDateTime();
        LocalDateTime end = start.plusMinutes(30); // Assuming each appointment lasts for 30 mins
        LocalDateTime bufferBeforeStart = start.minusMinutes(30); // 30 mins before the appointment start

        List<Appointment> overLappingAppointments = appointmentRepository.findByProviderIdBetweenTimeSlots(
                appointment.getProviderId(),
                start,
                end,
                bufferBeforeStart,
                "Scheduled");
        if(!overLappingAppointments.isEmpty()){
            System.out.println("The time slot is not available. ");
            throw new IllegalStateException("The time slot is not available.");
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentByPatientId(String patentId) {
        return appointmentRepository.findAllByPatientId(patentId);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
          Appointment appointment = appointmentRepository.findById(appointmentId)
                  .orElseThrow(()->new RuntimeException("Appointment not found"));
          appointment.setStatus("Cancelled");
          appointmentRepository.save(appointment);
    }
}
