package com.depot.bus.service.impl;

import com.depot.bus.model.Bus;
import com.depot.bus.repo.BusRepository;
import com.depot.bus.service.BusService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus getBusById(long id) throws IllegalArgumentException {
        final Optional<Bus> optionalBus = busRepository.findById(id);

        if (optionalBus.isPresent())
            return optionalBus.get();
        else
            throw new IllegalArgumentException("Invalid Bus ID");
    }

    @Override
    public long createBus(String model, String number) {
        final Bus Bus = new Bus(model, number);
        final Bus savedBus = busRepository.save(Bus);

        return savedBus.getId();
    }

    @Override
    public void updateBus(long id, String model, String number)
            throws IllegalArgumentException {
        final Optional<Bus> optionalBus = busRepository.findById(id);

        if (optionalBus.isEmpty())
            throw new IllegalArgumentException("Invalid Bus ID");

        final Bus Bus = optionalBus.get();
        if (model != null && !model.isBlank()) Bus.setModel(model);
        if (number != null && !number.isBlank()) Bus.setNumber(number);
        busRepository.save(Bus);
    }

    @Override
    public void deleteBus(long id) {
        busRepository.deleteById(id);
    }
}
