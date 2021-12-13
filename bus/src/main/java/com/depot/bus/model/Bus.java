package com.depot.bus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
final public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String model;
    private String number;

    public Bus() {

    }

    public Long getId() {
        return id;
    }

    public Bus(String model, String number) {
        this.model = model;
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
