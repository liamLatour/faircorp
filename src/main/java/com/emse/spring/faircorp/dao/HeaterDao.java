package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeaterDao extends JpaRepository<Heater, Long> {
    Heater findById(@Param("id") String name);

    @Modifying
    @Query("delete from Heater c where c.room.id=:id")
    void deleteByRoom(@Param("id") Long id);
}