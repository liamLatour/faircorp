package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HeaterDao extends JpaRepository<Heater, Long> {
    Heater findById(@Param("id") String name);
}