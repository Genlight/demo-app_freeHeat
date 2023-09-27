package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingSchedule;
import com.genser.demo_app.domain.User;
import com.genser.demo_app.domain.Vehicle;
import com.genser.demo_app.model.ChargingScheduleDTO;
import com.genser.demo_app.repos.ChargingScheduleRepository;
import com.genser.demo_app.repos.UserRepository;
import com.genser.demo_app.repos.VehicleRepository;
import com.genser.demo_app.util.NotFoundException;
import com.genser.demo_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChargingScheduleService {

    private final ChargingScheduleRepository chargingScheduleRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public ChargingScheduleService(final ChargingScheduleRepository chargingScheduleRepository,
            final UserRepository userRepository, final VehicleRepository vehicleRepository) {
        this.chargingScheduleRepository = chargingScheduleRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ChargingScheduleDTO> findAll() {
        final List<ChargingSchedule> chargingSchedules = chargingScheduleRepository.findAll(Sort.by("id"));
        return chargingSchedules.stream()
                .map(chargingSchedule -> mapToDTO(chargingSchedule, new ChargingScheduleDTO()))
                .toList();
    }

    public ChargingScheduleDTO get(final Long id) {
        return chargingScheduleRepository.findById(id)
                .map(chargingSchedule -> mapToDTO(chargingSchedule, new ChargingScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChargingScheduleDTO chargingScheduleDTO) {
        final ChargingSchedule chargingSchedule = new ChargingSchedule();
        mapToEntity(chargingScheduleDTO, chargingSchedule);
        return chargingScheduleRepository.save(chargingSchedule).getId();
    }

    public void update(final Long id, final ChargingScheduleDTO chargingScheduleDTO) {
        final ChargingSchedule chargingSchedule = chargingScheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chargingScheduleDTO, chargingSchedule);
        chargingScheduleRepository.save(chargingSchedule);
    }

    public void delete(final Long id) {
        chargingScheduleRepository.deleteById(id);
    }

    private ChargingScheduleDTO mapToDTO(final ChargingSchedule chargingSchedule,
            final ChargingScheduleDTO chargingScheduleDTO) {
        chargingScheduleDTO.setId(chargingSchedule.getId());
        chargingScheduleDTO.setUniqueId(chargingSchedule.getUniqueId());
        chargingScheduleDTO.setUser(chargingSchedule.getUser() == null ? null : chargingSchedule.getUser().getId());
        return chargingScheduleDTO;
    }

    private ChargingSchedule mapToEntity(final ChargingScheduleDTO chargingScheduleDTO,
            final ChargingSchedule chargingSchedule) {
        chargingSchedule.setUniqueId(chargingScheduleDTO.getUniqueId());
        final User user = chargingScheduleDTO.getUser() == null ? null : userRepository.findById(chargingScheduleDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        chargingSchedule.setUser(user);
        return chargingSchedule;
    }

    public String getReferencedWarning(final Long id) {
        final ChargingSchedule chargingSchedule = chargingScheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Vehicle chargingScheduleVehicle = vehicleRepository.findFirstByChargingSchedule(chargingSchedule);
        if (chargingScheduleVehicle != null) {
            return WebUtils.getMessage("chargingSchedule.vehicle.chargingSchedule.referenced", chargingScheduleVehicle.getId());
        }
        return null;
    }

}
