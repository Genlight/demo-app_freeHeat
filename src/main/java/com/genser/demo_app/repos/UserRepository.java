package com.genser.demo_app.repos;

import com.genser.demo_app.domain.PreferencesCharging;
import com.genser.demo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPreferencesChargingId(Long id);

    User findFirstByPreferencesCharging(PreferencesCharging preferencesCharging);

    User getByEmail(String email);
}
