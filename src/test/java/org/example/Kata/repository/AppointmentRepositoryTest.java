package org.example.Kata.repository;

import org.example.Kata.model.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

@DataJpaTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRespoitory;



    @Test
    void shouldSaveAppointment(){
        Appointment appointment = new Appointment();
        appointment.setPatientId("123456");
        appointment.setProviderId("789012");
        appointment.setDateTime(LocalDateTime.of(2023,12,31,10,0));
        appointment.setStatus("Scehduled");
        Appointment savedAppointment =  appointmentRespoitory.save(appointment);
        assertThat(savedAppointment).isNotNull();
        assertThat(savedAppointment.getId()).isNotNull();
    }
}
