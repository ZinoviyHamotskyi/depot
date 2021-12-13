package com.depot.appointment.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Long adminId;
    private Long driverId;
    private Long busId;
    private Boolean accepted;

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public Appointment(Long adminId, Long driverId, Long busId) {
        this.adminId = adminId;
        this.driverId = driverId;
        this.busId = busId;
        this.accepted = Boolean.FALSE;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
