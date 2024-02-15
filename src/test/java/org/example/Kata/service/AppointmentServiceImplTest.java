package org.example.Kata.service;


import org.example.Kata.model.Appointment;
import org.example.Kata.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;


    @BeforeEach
    void setUp(){
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId("123457");
        appointment.setProviderId("789013");
        //appointment.setDateTime(LocalDateTime.now());
        appointment.setDateTime(LocalDateTime.of(2023,12,31,10,00));
        appointment.setStatus("Scheduled");
    }

    @Test
    void scheduleAppointment_Success(){
         // Mock findByProviderIdAndDateTime to return an empty list, indicating no overlapping appointments
        when(appointmentRepository.findByProviderIdBetweenTimeSlots(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(Collections.emptyList());

        // Mock save method to return the appointment
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment scheduledAppointment = appointmentService.scheduleAppointmentWithAvailableSlots(appointment);

        assertNotNull(scheduledAppointment);
        assertEquals(appointment.getProviderId(), scheduledAppointment.getProviderId());

    }

    @Test
    void scheduleOverlappingAppointmentThrowsException(){
        // Mock findByProviderIdAndDateTime to return a non-empty list, indicating an overlapping appointment exists
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        Mockito.lenient().when(appointmentRepository.findByProviderIdBetweenTimeSlots(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(appointmentList);

        assertThrows(IllegalStateException.class, () -> appointmentService.scheduleAppointmentWithAvailableSlots(appointment),
                "The time slot is not available.");
    }

    @Test
    void scheduleAppointment_NullAppointment_failure(){
        assertThrows(IllegalArgumentException.class,()->appointmentService.scheduleAppointmentWithAvailableSlots(null),"Appointment cannot be null.");
    }

    @Test
    void cancelAppointment_Success(){
        Mockito.lenient().when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        appointmentService.cancelAppointment(appointment.getId());
        assertEquals("Cancelled", appointment.getStatus(), "Appointment status should be 'Cancelled'");
    }

    @Test
    void cancelAppointment_AppointmentNotFound_ThrowsException() {
        Mockito.lenient().when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> appointmentService.cancelAppointment(1L), "Expected to throw, but it did not");
    }

    @Test
    void getAppointmentByPatientId_ReturnsAppointments() {
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        List<Appointment> mockAppointments = Arrays.asList(appointment1, appointment2);
        when(appointmentRepository.findAllByPatientId("123")).thenReturn(mockAppointments);

        // Execute the service call
        List<Appointment> returnedAppointments = appointmentService.getAppointmentByPatientId("123");

        // Assert the response
        assertEquals(2, returnedAppointments.size(), "Should return 2 appointments");
    }

    @Test
    void getAppointmentByPatientId_NoAppointmentsFound() {
        // Setup mock repository to return an empty list
        Mockito.lenient().when(appointmentRepository.findAllByPatientId("unknown")).thenReturn(Arrays.asList());

        // Execute the service call
        List<Appointment> returnedAppointments = appointmentService.getAppointmentByPatientId("unknown");

        // Assert the response
        assertTrue(returnedAppointments.isEmpty(), "Should return an empty list");
    }



}
