package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WindowDao extends JpaRepository<Window, Long>, WindowDaoCustom {
    Window findById(@Param("id") String name);

    @Query("select c from Window c where c.name=:name")
    Window findByName(@Param("name") String name);

    @Modifying
    @Query("delete from Window c where c.room.id=:id")
    void deleteByRoom(@Param("id") Long id);
}