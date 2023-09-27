package com.genser.demo_app.controller;

import com.genser.demo_app.model.ChargingLevelDTO;
import com.genser.demo_app.service.ChargingLevelService;
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
@RequestMapping("/chargingLevels")
public class ChargingLevelController {

    private final ChargingLevelService chargingLevelService;

    public ChargingLevelController(final ChargingLevelService chargingLevelService) {
        this.chargingLevelService = chargingLevelService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("chargingLevels", chargingLevelService.findAll());
        return "chargingLevel/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("chargingLevel") final ChargingLevelDTO chargingLevelDTO) {
        return "chargingLevel/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("chargingLevel") @Valid final ChargingLevelDTO chargingLevelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingLevel/add";
        }
        chargingLevelService.create(chargingLevelDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingLevel.create.success"));
        return "redirect:/chargingLevels";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("chargingLevel", chargingLevelService.get(id));
        return "chargingLevel/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("chargingLevel") @Valid final ChargingLevelDTO chargingLevelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingLevel/edit";
        }
        chargingLevelService.update(id, chargingLevelDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingLevel.update.success"));
        return "redirect:/chargingLevels";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = chargingLevelService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            chargingLevelService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("chargingLevel.delete.success"));
        }
        return "redirect:/chargingLevels";
    }

}
