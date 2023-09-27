package com.genser.demo_app.repos;

import com.genser.demo_app.domain.ChargingSchedule;
import com.genser.demo_app.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByDeviceId(Long id);

    boolean existsByChargingScheduleId(Long id);

    Vehicle findFirstByChargingSchedule(ChargingSchedule chargingSchedule);

}
