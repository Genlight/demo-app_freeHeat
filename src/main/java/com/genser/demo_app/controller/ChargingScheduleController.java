package com.genser.demo_app.controller;

import com.genser.demo_app.domain.User;
import com.genser.demo_app.model.ChargingScheduleDTO;
import com.genser.demo_app.repos.UserRepository;
import com.genser.demo_app.service.ChargingScheduleService;
import com.genser.demo_app.util.CustomCollectors;
import com.genser.demo_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/chargingSchedules")
public class ChargingScheduleController {

    private final ChargingScheduleService chargingScheduleService;
    private final UserRepository userRepository;

    public ChargingScheduleController(final ChargingScheduleService chargingScheduleService,
            final UserRepository userRepository) {
        this.chargingScheduleService = chargingScheduleService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("chargingSchedules", chargingScheduleService.findAll());
        return "chargingSchedule/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("chargingSchedule") final ChargingScheduleDTO chargingScheduleDTO) {
        return "chargingSchedule/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("chargingSchedule") @Valid final ChargingScheduleDTO chargingScheduleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingSchedule/add";
        }
        chargingScheduleService.create(chargingScheduleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingSchedule.create.success"));
        return "redirect:/chargingSchedules";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("chargingSchedule", chargingScheduleService.get(id));
        return "chargingSchedule/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("chargingSchedule") @Valid final ChargingScheduleDTO chargingScheduleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingSchedule/edit";
        }
        chargingScheduleService.update(id, chargingScheduleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingSchedule.update.success"));
        return "redirect:/chargingSchedules";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = chargingScheduleService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            chargingScheduleService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("chargingSchedule.delete.success"));
        }
        return "redirect:/chargingSchedules";
    }

}
