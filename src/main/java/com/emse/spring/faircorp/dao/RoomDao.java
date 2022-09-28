package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoomDao extends JpaRepository<Room, Long> {
    Room findById(@Param("id") String name);
}