package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingSchedule;
import com.genser.demo_app.domain.Device;
import com.genser.demo_app.domain.PreferencesCharging;
import com.genser.demo_app.domain.User;
import com.genser.demo_app.model.UserDTO;
import com.genser.demo_app.repos.ChargingScheduleRepository;
import com.genser.demo_app.repos.DeviceRepository;
import com.genser.demo_app.repos.PreferencesChargingRepository;
import com.genser.demo_app.repos.UserRepository;
import com.genser.demo_app.util.NotFoundException;
import com.genser.demo_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PreferencesChargingRepository preferencesChargingRepository;
    private final DeviceRepository deviceRepository;
    private final ChargingScheduleRepository chargingScheduleRepository;

    public UserService(final UserRepository userRepository,
            final PreferencesChargingRepository preferencesChargingRepository,
            final DeviceRepository deviceRepository,
            final ChargingScheduleRepository chargingScheduleRepository) {
        this.userRepository = userRepository;
        this.preferencesChargingRepository = preferencesChargingRepository;
        this.deviceRepository = deviceRepository;
        this.chargingScheduleRepository = chargingScheduleRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public UserDTO getByEmail(String email) {
        return mapToDTO(userRepository.getByEmail(email), (new UserDTO()));
    }
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(user.getIsAdmin());
        userDTO.setPreferencesCharging(user.getPreferencesCharging() == null ? null : user.getPreferencesCharging().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setIsAdmin(userDTO.getIsAdmin());
        final PreferencesCharging preferencesCharging = userDTO.getPreferencesCharging() == null ? null : preferencesChargingRepository.findById(userDTO.getPreferencesCharging())
                .orElseThrow(() -> new NotFoundException("preferencesCharging not found"));
        user.setPreferencesCharging(preferencesCharging);
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean preferencesChargingExists(final Long id) {
        return userRepository.existsByPreferencesChargingId(id);
    }

    public String getReferencedWarning(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Device userDevice = deviceRepository.findFirstByUser(user);
        if (userDevice != null) {
            return WebUtils.getMessage("user.device.user.referenced", userDevice.getId());
        }
        final ChargingSchedule userChargingSchedule = chargingScheduleRepository.findFirstByUser(user);
        if (userChargingSchedule != null) {
            return WebUtils.getMessage("user.chargingSchedule.user.referenced", userChargingSchedule.getId());
        }
        return null;
    }

}
