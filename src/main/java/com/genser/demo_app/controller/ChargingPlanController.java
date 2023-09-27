package com.genser.demo_app.controller;

import com.genser.demo_app.model.ChargingPlanDTO;
import com.genser.demo_app.service.ChargingPlanService;
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
@RequestMapping("/chargingPlans")
public class ChargingPlanController {

    private final ChargingPlanService chargingPlanService;

    public ChargingPlanController(final ChargingPlanService chargingPlanService) {
        this.chargingPlanService = chargingPlanService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("chargingPlans", chargingPlanService.findAll());
        return "chargingPlan/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("chargingPlan") final ChargingPlanDTO chargingPlanDTO) {
        return "chargingPlan/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("chargingPlan") @Valid final ChargingPlanDTO chargingPlanDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingPlan/add";
        }
        chargingPlanService.create(chargingPlanDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingPlan.create.success"));
        return "redirect:/chargingPlans";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("chargingPlan", chargingPlanService.get(id));
        return "chargingPlan/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("chargingPlan") @Valid final ChargingPlanDTO chargingPlanDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "chargingPlan/edit";
        }
        chargingPlanService.update(id, chargingPlanDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chargingPlan.update.success"));
        return "redirect:/chargingPlans";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        chargingPlanService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("chargingPlan.delete.success"));
        return "redirect:/chargingPlans";
    }

}
