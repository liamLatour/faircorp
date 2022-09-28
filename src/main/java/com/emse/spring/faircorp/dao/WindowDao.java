package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WindowDao extends JpaRepository<Window, Long> {
    Window findById(@Param("id") String name);
}
