package com.genser.demo_app.repos;

import com.genser.demo_app.domain.ChargingSchedule;
import com.genser.demo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChargingScheduleRepository extends JpaRepository<ChargingSchedule, Long> {

    ChargingSchedule findFirstByUser(User user);

}
