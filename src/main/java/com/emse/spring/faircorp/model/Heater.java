package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
@Table(name="HEATER")
public class Heater {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long power;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeaterStatus heater_status;

    @ManyToOne(optional = false)
    private Room room;

    public Heater() {
    }

    public Heater(String name, Room room, HeaterStatus heater_status) {
        this.heater_status = heater_status;
        this.name = name;
        this.room = room;
    }
}