package com.genser.demo_app.repos;

import com.genser.demo_app.domain.ChargingPlan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChargingPlanRepository extends JpaRepository<ChargingPlan, Long> {
}
