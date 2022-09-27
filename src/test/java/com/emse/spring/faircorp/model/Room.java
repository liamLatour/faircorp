package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="RROOM")
public class Room {
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String name;

    private Double currentTemperature;

    private Double targetTemperature;

    @OneToMany
    private List<Heater> heaters;

    @OneToMany
    private List<Window> windows;

    public Room() {
    }

    public Room(String name, Integer floor) {
        this.floor = floor;
        this.name = name;
    }
}