package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingSchedule;
import com.genser.demo_app.domain.Device;
import com.genser.demo_app.domain.Vehicle;
import com.genser.demo_app.model.VehicleDTO;
import com.genser.demo_app.repos.ChargingScheduleRepository;
import com.genser.demo_app.repos.DeviceRepository;
import com.genser.demo_app.repos.VehicleRepository;
import com.genser.demo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DeviceRepository deviceRepository;
    private final ChargingScheduleRepository chargingScheduleRepository;

    public VehicleService(final VehicleRepository vehicleRepository,
            final DeviceRepository deviceRepository,
            final ChargingScheduleRepository chargingScheduleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.deviceRepository = deviceRepository;
        this.chargingScheduleRepository = chargingScheduleRepository;
    }

    public List<VehicleDTO> findAll() {
        final List<Vehicle> vehicles = vehicleRepository.findAll(Sort.by("id"));
        return vehicles.stream()
                .map(vehicle -> mapToDTO(vehicle, new VehicleDTO()))
                .toList();
    }

    public VehicleDTO get(final Long id) {
        return vehicleRepository.findById(id)
                .map(vehicle -> mapToDTO(vehicle, new VehicleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VehicleDTO vehicleDTO) {
        final Vehicle vehicle = new Vehicle();
        mapToEntity(vehicleDTO, vehicle);
        return vehicleRepository.save(vehicle).getId();
    }

    public void update(final Long id, final VehicleDTO vehicleDTO) {
        final Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vehicleDTO, vehicle);
        vehicleRepository.save(vehicle);
    }

    public void delete(final Long id) {
        vehicleRepository.deleteById(id);
    }

    private VehicleDTO mapToDTO(final Vehicle vehicle, final VehicleDTO vehicleDTO) {
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setName(vehicle.getName());
        vehicleDTO.setDevice(vehicle.getDevice() == null ? null : vehicle.getDevice().getId());
        vehicleDTO.setChargingSchedule(vehicle.getChargingSchedule() == null ? null : vehicle.getChargingSchedule().getId());
        return vehicleDTO;
    }

    private Vehicle mapToEntity(final VehicleDTO vehicleDTO, final Vehicle vehicle) {
        vehicle.setName(vehicleDTO.getName());
        final Device device = vehicleDTO.getDevice() == null ? null : deviceRepository.findById(vehicleDTO.getDevice())
                .orElseThrow(() -> new NotFoundException("device not found"));
        vehicle.setDevice(device);
        final ChargingSchedule chargingSchedule = vehicleDTO.getChargingSchedule() == null ? null : chargingScheduleRepository.findById(vehicleDTO.getChargingSchedule())
                .orElseThrow(() -> new NotFoundException("chargingSchedule not found"));
        vehicle.setChargingSchedule(chargingSchedule);
        return vehicle;
    }

    public boolean deviceExists(final Long id) {
        return vehicleRepository.existsByDeviceId(id);
    }

    public boolean chargingScheduleExists(final Long id) {
        return vehicleRepository.existsByChargingScheduleId(id);
    }

}
