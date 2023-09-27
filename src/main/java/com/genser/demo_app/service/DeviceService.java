package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingLevel;
import com.genser.demo_app.domain.Device;
import com.genser.demo_app.domain.User;
import com.genser.demo_app.model.DeviceDTO;
import com.genser.demo_app.repos.ChargingLevelRepository;
import com.genser.demo_app.repos.DeviceRepository;
import com.genser.demo_app.repos.UserRepository;
import com.genser.demo_app.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final ChargingLevelRepository chargingLevelRepository;

    public DeviceService(final DeviceRepository deviceRepository,
            final UserRepository userRepository,
            final ChargingLevelRepository chargingLevelRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.chargingLevelRepository = chargingLevelRepository;
    }

    public List<DeviceDTO> findAll() {
        final List<Device> devices = deviceRepository.findAll(Sort.by("id"));
        return devices.stream()
                .map(device -> mapToDTO(device, new DeviceDTO()))
                .toList();
    }

    public DeviceDTO get(final Long id) {
        return deviceRepository.findById(id)
                .map(device -> mapToDTO(device, new DeviceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DeviceDTO deviceDTO) {
        final Device device = new Device();
        mapToEntity(deviceDTO, device);
        return deviceRepository.save(device).getId();
    }

    public void update(final Long id, final DeviceDTO deviceDTO) {
        final Device device = deviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(deviceDTO, device);
        deviceRepository.save(device);
    }

    public void delete(final Long id) {
        deviceRepository.deleteById(id);
    }

    public List<DeviceDTO> findAllByUser_Id(final Long id) {
        return deviceRepository.findByUser_IdIs(id).stream().map(device ->  mapToDTO(device, new DeviceDTO())).collect(Collectors.toList());
    }
    private DeviceDTO mapToDTO(final Device device, final DeviceDTO deviceDTO) {
        deviceDTO.setId(device.getId());
        deviceDTO.setDescription(device.getDescription());
        deviceDTO.setType(device.getType());
        deviceDTO.setStatus(device.getStatus());
        deviceDTO.setEnergyPrice(device.getEnergyPrice());
        deviceDTO.setUser(device.getUser() == null ? null : device.getUser().getId());
        deviceDTO.setChargingLevel(device.getChargingLevel() == null ? null : device.getChargingLevel().getId());
        if (deviceDTO.getChargingLevel() == null) {
            deviceDTO.setBatteryChargeLevel(0);
        } else {
            deviceDTO.setBatteryChargeLevel(device.getChargingLevel().getPercentage());
        }
        return deviceDTO;
    }

    private Device mapToEntity(final DeviceDTO deviceDTO, final Device device) {
        device.setDescription(deviceDTO.getDescription());
        device.setType(deviceDTO.getType());
        device.setStatus(deviceDTO.getStatus());
        device.setEnergyPrice(deviceDTO.getEnergyPrice());

        final User user = deviceDTO.getUser() == null ? null : userRepository.findById(deviceDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        device.setUser(user);
        final ChargingLevel chargingLevel = deviceDTO.getChargingLevel() == null ? null : chargingLevelRepository.findById(deviceDTO.getChargingLevel())
                .orElseThrow(() -> new NotFoundException("chargingLevel not found"));
        device.setBatteryChargeLevel(chargingLevel.getPercentage());
        device.setChargingLevel(chargingLevel);
        return device;
    }

    public boolean chargingLevelExists(final Long id) {
        return deviceRepository.existsByChargingLevelId(id);
    }

}
