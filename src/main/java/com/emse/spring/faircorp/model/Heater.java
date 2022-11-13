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
    private HeaterStatus heaterStatus;

    @ManyToOne(optional = false)
    private Room room;

    public Heater() {
    }

    public Heater(Room room, String name, HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
        this.name = name;
        this.room = room;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public Long getPower() {
        return this.power;
    }
    public void setPower(Long power) {
        this.power = power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom(){
        return this.room;
    }

    public HeaterStatus getHeaterStatus() {
        return this.heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}