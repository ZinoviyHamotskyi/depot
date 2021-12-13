package com.depot.bus.api;

import com.depot.bus.dto.BusDto;
import com.depot.bus.model.Bus;
import com.depot.bus.service.impl.BusServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/bus")
@RestController
public class BusController {
    private final BusServiceImpl busService;

    @GetMapping
    public ResponseEntity<List<Bus>> getAll() {
        final List<Bus> buss = busService.getAll();
        return ResponseEntity.ok(buss);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getById(@PathVariable long id) {
        try {
            Bus bus = busService.getBusById(id);

            return ResponseEntity.ok(bus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<BusDto> getDtoById(@PathVariable long id) {
        try {
            Bus bus = busService.getBusById(id);
            BusDto busDto = new BusDto(bus.getModel(), bus.getNumber());

            return ResponseEntity.ok(busDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody BusDto busDto) {
        final String model = busDto.getModel();
        final String number = busDto.getNumber();

        final long busId = busService.createBus(model, number);
        final String busUri = "/bus" + busId;

        return ResponseEntity.created(URI.create(busUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody BusDto busDto) {
        final String model = busDto.getModel();
        final String number = busDto.getNumber();

        try {
            busService.updateBus(id, model, number);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        busService.deleteBus(id);

        return ResponseEntity.noContent().build();
    }
}
