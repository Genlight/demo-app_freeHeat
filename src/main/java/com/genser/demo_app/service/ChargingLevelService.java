package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingLevel;
import com.genser.demo_app.domain.Device;
import com.genser.demo_app.model.ChargingLevelDTO;
import com.genser.demo_app.repos.ChargingLevelRepository;
import com.genser.demo_app.repos.DeviceRepository;
import com.genser.demo_app.util.NotFoundException;
import com.genser.demo_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChargingLevelService {

    private final ChargingLevelRepository chargingLevelRepository;
    private final DeviceRepository deviceRepository;

    public ChargingLevelService(final ChargingLevelRepository chargingLevelRepository,
            final DeviceRepository deviceRepository) {
        this.chargingLevelRepository = chargingLevelRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<ChargingLevelDTO> findAll() {
        final List<ChargingLevel> chargingLevels = chargingLevelRepository.findAll(Sort.by("id"));
        return chargingLevels.stream()
                .map(chargingLevel -> mapToDTO(chargingLevel, new ChargingLevelDTO()))
                .toList();
    }

    public ChargingLevelDTO get(final Long id) {
        return chargingLevelRepository.findById(id)
                .map(chargingLevel -> mapToDTO(chargingLevel, new ChargingLevelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChargingLevelDTO chargingLevelDTO) {
        final ChargingLevel chargingLevel = new ChargingLevel();
        mapToEntity(chargingLevelDTO, chargingLevel);
        return chargingLevelRepository.save(chargingLevel).getId();
    }

    public void update(final Long id, final ChargingLevelDTO chargingLevelDTO) {
        final ChargingLevel chargingLevel = chargingLevelRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chargingLevelDTO, chargingLevel);
        chargingLevelRepository.save(chargingLevel);
    }

    public void delete(final Long id) {
        chargingLevelRepository.deleteById(id);
    }

    private ChargingLevelDTO mapToDTO(final ChargingLevel chargingLevel,
            final ChargingLevelDTO chargingLevelDTO) {
        chargingLevelDTO.setId(chargingLevel.getId());
        chargingLevelDTO.setPercentage(chargingLevel.getPercentage());
        return chargingLevelDTO;
    }

    private ChargingLevel mapToEntity(final ChargingLevelDTO chargingLevelDTO,
            final ChargingLevel chargingLevel) {
        chargingLevel.setPercentage(chargingLevelDTO.getPercentage());
        return chargingLevel;
    }

    public String getReferencedWarning(final Long id) {
        final ChargingLevel chargingLevel = chargingLevelRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Device chargingLevelDevice = deviceRepository.findFirstByChargingLevel(chargingLevel);
        if (chargingLevelDevice != null) {
            return WebUtils.getMessage("chargingLevel.device.chargingLevel.referenced", chargingLevelDevice.getId());
        }
        return null;
    }

}
