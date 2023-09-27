package com.genser.demo_app.controller;

import com.genser.demo_app.model.PreferencesChargingDTO;
import com.genser.demo_app.service.PreferencesChargingService;
import com.genser.demo_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/preferencesChargings")
public class PreferencesChargingController {

    private final PreferencesChargingService preferencesChargingService;

    public PreferencesChargingController(
            final PreferencesChargingService preferencesChargingService) {
        this.preferencesChargingService = preferencesChargingService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("preferencesChargings", preferencesChargingService.findAll());
        return "preferencesCharging/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("preferencesCharging") final PreferencesChargingDTO preferencesChargingDTO) {
        return "preferencesCharging/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("preferencesCharging") @Valid final PreferencesChargingDTO preferencesChargingDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "preferencesCharging/add";
        }
        preferencesChargingService.create(preferencesChargingDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("preferencesCharging.create.success"));
        return "redirect:/preferencesChargings";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("preferencesCharging", preferencesChargingService.get(id));
        return "preferencesCharging/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("preferencesCharging") @Valid final PreferencesChargingDTO preferencesChargingDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "preferencesCharging/edit";
        }
        preferencesChargingService.update(id, preferencesChargingDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("preferencesCharging.update.success"));
        return "redirect:/preferencesChargings";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = preferencesChargingService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            preferencesChargingService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("preferencesCharging.delete.success"));
        }
        return "redirect:/preferencesChargings";
    }

}
