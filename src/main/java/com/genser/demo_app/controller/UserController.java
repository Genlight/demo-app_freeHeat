package com.genser.demo_app.controller;

import com.genser.demo_app.domain.PreferencesCharging;
import com.genser.demo_app.model.UserDTO;
import com.genser.demo_app.repos.PreferencesChargingRepository;
import com.genser.demo_app.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PreferencesChargingRepository preferencesChargingRepository;

    public UserController(final UserService userService,
            final PreferencesChargingRepository preferencesChargingRepository) {
        this.userService = userService;
        this.preferencesChargingRepository = preferencesChargingRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("preferencesChargingValues", preferencesChargingRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PreferencesCharging::getId, PreferencesCharging::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("email") && userService.emailExists(userDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.user.email");
        }
        if (!bindingResult.hasFieldErrors("preferencesCharging") && userDTO.getPreferencesCharging() != null && userService.preferencesChargingExists(userDTO.getPreferencesCharging())) {
            bindingResult.rejectValue("preferencesCharging", "Exists.user.preferencesCharging");
        }
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("user") @Valid final UserDTO userDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        final UserDTO currentUserDTO = userService.get(id);
        if (!bindingResult.hasFieldErrors("email") &&
                !userDTO.getEmail().equalsIgnoreCase(currentUserDTO.getEmail()) &&
                userService.emailExists(userDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.user.email");
        }
        if (!bindingResult.hasFieldErrors("preferencesCharging") && userDTO.getPreferencesCharging() != null &&
                !userDTO.getPreferencesCharging().equals(currentUserDTO.getPreferencesCharging()) &&
                userService.preferencesChargingExists(userDTO.getPreferencesCharging())) {
            bindingResult.rejectValue("preferencesCharging", "Exists.user.preferencesCharging");
        }
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = userService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            userService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        }
        return "redirect:/users";
    }

}
