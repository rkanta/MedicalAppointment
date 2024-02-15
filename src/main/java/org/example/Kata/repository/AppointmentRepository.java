package org.example.Kata.repository;

import org.example.Kata.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByPatientId(String patientId);

    //List<Appointment> findByProviderIdAndDateTime(String providerId, LocalDateTime start);

    @Transactional
    @Query("SELECT a FROM Appointment a WHERE a.providerId = :providerId " +
            "AND ((a.dateTime >= :start AND a.dateTime < :end) " +
            "OR (a.dateTime < :start AND a.dateTime > :bufferBeforeStart))" +
            "AND a.status = :status")
    List<Appointment> findByProviderIdBetweenTimeSlots(
            @Param("providerId") String providerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("bufferBeforeStart") LocalDateTime bufferBeforeStart,
            @Param("status") String status
    );
}
