package org.example.Kata.controller;


import org.example.Kata.model.Appointment;
import org.example.Kata.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {


    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    void scheduleAppointment_ReturnsAppointment_Success() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setPatientId("123");
        when(appointmentService.scheduleAppointmentWithAvailableSlots(any(Appointment.class))).thenReturn(appointment);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\": \"123\", \"providerId\": \"456\", \"dateTime\": \"2023-12-31T10:00:00\", \"status\": \"Scheduled\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value("123"));
    }

    @Test
    void getAppointmentByPatientId_ReturnsAppointments_Success() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setPatientId("123");
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentService.getAppointmentByPatientId(anyString())).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value("123"));
    }


    @Test
    void cancelAppointment_Success() throws Exception {
        doNothing().when(appointmentService).cancelAppointment(anyLong());

        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).cancelAppointment(1L);
    }


}
