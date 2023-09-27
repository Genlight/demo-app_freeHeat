package com.genser.demo_app.service;

import com.genser.demo_app.domain.ChargingPlan;
import com.genser.demo_app.model.ChargingPlanDTO;
import com.genser.demo_app.repos.ChargingPlanRepository;
import com.genser.demo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChargingPlanService {

    private final ChargingPlanRepository chargingPlanRepository;

    public ChargingPlanService(final ChargingPlanRepository chargingPlanRepository) {
        this.chargingPlanRepository = chargingPlanRepository;
    }

    public List<ChargingPlanDTO> findAll() {
        final List<ChargingPlan> chargingPlans = chargingPlanRepository.findAll(Sort.by("id"));
        return chargingPlans.stream()
                .map(chargingPlan -> mapToDTO(chargingPlan, new ChargingPlanDTO()))
                .toList();
    }

    public ChargingPlanDTO get(final Long id) {
        return chargingPlanRepository.findById(id)
                .map(chargingPlan -> mapToDTO(chargingPlan, new ChargingPlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChargingPlanDTO chargingPlanDTO) {
        final ChargingPlan chargingPlan = new ChargingPlan();
        mapToEntity(chargingPlanDTO, chargingPlan);
        return chargingPlanRepository.save(chargingPlan).getId();
    }

    public void update(final Long id, final ChargingPlanDTO chargingPlanDTO) {
        final ChargingPlan chargingPlan = chargingPlanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chargingPlanDTO, chargingPlan);
        chargingPlanRepository.save(chargingPlan);
    }

    public void delete(final Long id) {
        chargingPlanRepository.deleteById(id);
    }

    private ChargingPlanDTO mapToDTO(final ChargingPlan chargingPlan,
            final ChargingPlanDTO chargingPlanDTO) {
        chargingPlanDTO.setId(chargingPlan.getId());
        chargingPlanDTO.setNextCharge(chargingPlan.getNextCharge());
        return chargingPlanDTO;
    }

    private ChargingPlan mapToEntity(final ChargingPlanDTO chargingPlanDTO,
            final ChargingPlan chargingPlan) {
        chargingPlan.setNextCharge(chargingPlanDTO.getNextCharge());
        return chargingPlan;
    }

}
