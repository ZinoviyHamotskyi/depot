package com.depot.bus.service;

import com.depot.bus.model.Bus;

import java.util.List;

public interface BusService {
    List<Bus> getAll();
    Bus getBusById(long id) throws IllegalArgumentException;
    long createBus(String model, String number);
    void updateBus(long id, String model, String number)
            throws IllegalArgumentException;
    void deleteBus(long id);
}
