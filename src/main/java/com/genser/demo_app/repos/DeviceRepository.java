package com.genser.demo_app.repos;

import com.genser.demo_app.domain.ChargingLevel;
import com.genser.demo_app.domain.Device;
import com.genser.demo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByChargingLevelId(Long id);

    Device findFirstByUser(User user);

    Device findFirstByChargingLevel(ChargingLevel chargingLevel);

    List<Device> findByUser_IdIs(Long id);



}
