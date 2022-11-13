package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingDao extends JpaRepository<Building, Long> {
    Building findById(@Param("id") String name);

    @Query("select c from Building c where c.name=:name or c.address=:name")
    List<Building> findByNameOrAddress(@Param("name") String name);
}
