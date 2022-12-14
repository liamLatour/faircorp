package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ROOM")
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer floor;

    private Double currentTemperature;
    private Double targetTemperature;

    @OneToMany(mappedBy = "room")
    private List<Heater> heaters;

    @OneToMany(mappedBy = "room")
    private List<Window> windows;

    @ManyToOne(optional = false)
    private Building building;

    public Room() {
    }

    public Room(Building building, String name, Integer floor, Double currentTemperature, Double targetTemperature) {
        this.building = building;
        this.floor = floor;
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentTemperature() {
        return this.currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return this.targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public Building getBuilding() {
        return this.building;
    }

    public List<Window> getWindows() {
        return this.windows;
    }

    public List<Heater> getHeaters() {
        return this.heaters;
    }
}