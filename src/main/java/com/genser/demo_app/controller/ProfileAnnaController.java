package com.genser.demo_app.controller;

import com.genser.demo_app.domain.ChargingLevel;
import com.genser.demo_app.domain.User;
import com.genser.demo_app.model.ChargingLevelDTO;
import com.genser.demo_app.model.DeviceDTO;
import com.genser.demo_app.model.PreferencesChargingDTO;
import com.genser.demo_app.model.UserDTO;
import com.genser.demo_app.service.ChargingLevelService;
import com.genser.demo_app.service.DeviceService;
import com.genser.demo_app.service.PreferencesChargingService;
import com.genser.demo_app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class ProfileAnnaController {

    private final DeviceService deviceService;
    private final UserService userService;
    private final Long userId;
    private final String annaEmail = "anna.m@gmail.com";
    private final PreferencesChargingService preferencesChargingService;
    private final ChargingLevelService chargingLevelService;

    public ProfileAnnaController(final DeviceService deviceService,
                                 final UserService userService,
                                 final PreferencesChargingService preferencesChargingService,
                                 final ChargingLevelService chargingLevelService) {

//         instantiate anna's profile
        this.userService = userService;
        this.deviceService = deviceService;
        this.preferencesChargingService = preferencesChargingService;
        this.chargingLevelService = chargingLevelService;
        if (userService.emailExists(annaEmail)) {
            final var userDTO = userService.getByEmail(annaEmail);
            this.userId = userDTO.getId();
        } else {
            final var userDTO = new UserDTO();
            userDTO.setName("Anna M.");
            userDTO.setEmail(annaEmail);
            userDTO.setIsAdmin(false);

            final var preferencesChargingDTO = new PreferencesChargingDTO();
            preferencesChargingDTO.setMaxCharge(95);
            preferencesChargingDTO.setMinCharge(10);
            preferencesChargingDTO.setMaxCost(new BigDecimal("0.8"));
            final var pCId = preferencesChargingService.create(preferencesChargingDTO);
            userDTO.setPreferencesCharging(pCId);
            this.userId = userService.create(userDTO);
        }
        if (deviceService.findAllByUser_Id(userId).isEmpty()) {
            final var deviceDTO1 = new DeviceDTO();
            deviceDTO1.setDescription("Tesla - Work");
            deviceDTO1.setChargingLevel(80L);
            deviceDTO1.setStatus(1);
            deviceDTO1.setUser(userId);
            deviceDTO1.setType("Tesla");

            final var chargingLevelDTO = new ChargingLevelDTO();
            chargingLevelDTO.setPercentage(40);
            final var chLId = chargingLevelService.create(chargingLevelDTO);
            deviceDTO1.setChargingLevel(chLId);
            deviceService.create(deviceDTO1);

            final var deviceDTO2 = new DeviceDTO();
            deviceDTO2.setDescription("Leaf - Husband");
            deviceDTO2.setChargingLevel(80L);
            deviceDTO2.setStatus(2);
            deviceDTO2.setUser(userId);
            deviceDTO2.setType("Nissan Leaf");

            chargingLevelDTO.setPercentage(60);
            final var chLId2 = chargingLevelService.create(chargingLevelDTO);
            deviceDTO2.setChargingLevel(chLId2);
            deviceService.create(deviceDTO2);
        }
    }

    @GetMapping("/profile_anna")
    public String getProfileAnna(Model model) {
        model.addAttribute("devices", deviceService.findAllByUser_Id(userId));
        model.addAttribute("userId", userId);
        return "profile/anna";
    }
}
