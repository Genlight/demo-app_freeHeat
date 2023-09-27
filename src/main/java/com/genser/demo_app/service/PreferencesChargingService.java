package com.genser.demo_app.service;

import com.genser.demo_app.domain.PreferencesCharging;
import com.genser.demo_app.domain.User;
import com.genser.demo_app.model.PreferencesChargingDTO;
import com.genser.demo_app.repos.PreferencesChargingRepository;
import com.genser.demo_app.repos.UserRepository;
import com.genser.demo_app.util.NotFoundException;
import com.genser.demo_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PreferencesChargingService {

    private final PreferencesChargingRepository preferencesChargingRepository;
    private final UserRepository userRepository;

    public PreferencesChargingService(
            final PreferencesChargingRepository preferencesChargingRepository,
            final UserRepository userRepository) {
        this.preferencesChargingRepository = preferencesChargingRepository;
        this.userRepository = userRepository;
    }

    public List<PreferencesChargingDTO> findAll() {
        final List<PreferencesCharging> preferencesChargings = preferencesChargingRepository.findAll(Sort.by("id"));
        return preferencesChargings.stream()
                .map(preferencesCharging -> mapToDTO(preferencesCharging, new PreferencesChargingDTO()))
                .toList();
    }

    public PreferencesChargingDTO get(final Long id) {
        return preferencesChargingRepository.findById(id)
                .map(preferencesCharging -> mapToDTO(preferencesCharging, new PreferencesChargingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PreferencesChargingDTO preferencesChargingDTO) {
        final PreferencesCharging preferencesCharging = new PreferencesCharging();
        mapToEntity(preferencesChargingDTO, preferencesCharging);
        return preferencesChargingRepository.save(preferencesCharging).getId();
    }

    public void update(final Long id, final PreferencesChargingDTO preferencesChargingDTO) {
        final PreferencesCharging preferencesCharging = preferencesChargingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(preferencesChargingDTO, preferencesCharging);
        preferencesChargingRepository.save(preferencesCharging);
    }

    public void delete(final Long id) {
        preferencesChargingRepository.deleteById(id);
    }

    private PreferencesChargingDTO mapToDTO(final PreferencesCharging preferencesCharging,
            final PreferencesChargingDTO preferencesChargingDTO) {
        preferencesChargingDTO.setId(preferencesCharging.getId());
        preferencesChargingDTO.setMaxCost(preferencesCharging.getMaxCost());
        preferencesChargingDTO.setMaxTime(preferencesCharging.getMaxTime());
        preferencesChargingDTO.setMaxCharge(preferencesCharging.getMaxCharge());
        preferencesChargingDTO.setMinCharge(preferencesCharging.getMinCharge());
        return preferencesChargingDTO;
    }

    private PreferencesCharging mapToEntity(final PreferencesChargingDTO preferencesChargingDTO,
            final PreferencesCharging preferencesCharging) {
        preferencesCharging.setMaxCost(preferencesChargingDTO.getMaxCost());
        preferencesCharging.setMaxTime(preferencesChargingDTO.getMaxTime());
        preferencesCharging.setMaxCharge(preferencesChargingDTO.getMaxCharge());
        preferencesCharging.setMinCharge(preferencesChargingDTO.getMinCharge());
        return preferencesCharging;
    }

    public String getReferencedWarning(final Long id) {
        final PreferencesCharging preferencesCharging = preferencesChargingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final User preferencesChargingUser = userRepository.findFirstByPreferencesCharging(preferencesCharging);
        if (preferencesChargingUser != null) {
            return WebUtils.getMessage("preferencesCharging.user.preferencesCharging.referenced", preferencesChargingUser.getId());
        }
        return null;
    }

}
