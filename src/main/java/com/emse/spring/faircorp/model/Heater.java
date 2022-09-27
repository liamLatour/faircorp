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
    @ManyToOne
    private Room room;

    @Enumerated
    @Column(nullable = false)
    private HeaterStatus heaterStatus;

    public Heater() {
    }

    public Heater(String name, Room room, HeaterStatus status) {
        this.heaterStatus = status;
        this.name = name;
        this.room = room;
    }
}