package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
@Table(name="RWINDOW")
public class Window {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WindowStatus window_status;

    @ManyToOne
    private Room room;

    public Window() {
    }

    public Window(String name, WindowStatus window_status, Room room) {
        this.window_status = window_status;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WindowStatus getWindow_status() {
        return window_status;
    }

    public void setWindow_status(WindowStatus windowStatus) {
        this.window_status = windowStatus;
    }
}