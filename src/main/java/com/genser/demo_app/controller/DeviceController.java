package com.genser.demo_app.controller;

import com.genser.demo_app.model.DeviceDTO;
import com.genser.demo_app.service.ChargingLevelService;
import com.genser.demo_app.service.DeviceService;
import com.genser.demo_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(final DeviceService deviceService, final ChargingLevelService chargingLevelService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String list (final Model model) {
        if (model.containsAttribute("userId")) {
            model.addAttribute("devices",
                    deviceService.findAllByUser_Id(
                            (Long) model.getAttribute("userId")));
        } else {
            model.addAttribute("devices", deviceService.findAll());
        }
        return "device/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("device") final DeviceDTO deviceDTO) {
        return "device/add";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("device") @Valid final DeviceDTO deviceDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "device/add";
        }

        deviceService.create(deviceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("device.create.success"));
        return "redirect:/devices";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("device", deviceService.get(id));
        return "device/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
                       @ModelAttribute("device") @Valid final DeviceDTO deviceDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "device/edit";
        }
        deviceService.update(id, deviceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("device.update.success"));
        return "redirect:/devices";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        deviceService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("device.delete.success"));
        return "redirect:/devices";
    }
}
