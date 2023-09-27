package com.genser.demo_app.repos;

import com.genser.demo_app.domain.PreferencesCharging;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PreferencesChargingRepository extends JpaRepository<PreferencesCharging, Long> {
}
