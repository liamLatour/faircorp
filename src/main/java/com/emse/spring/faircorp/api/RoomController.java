package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;
    private final RoomDao roomDao;

    private final BuildingDao buildingDao;

    public RoomController(BuildingDao buildingDao, HeaterDao heaterDao, WindowDao windowDao, RoomDao roomDao) {
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        // RoomDto must always contain a building
        Building building = buildingDao.findById(dto.getBuildingId()).orElseThrow(IllegalArgumentException::new);
        Room room = null;

        // On creation id is not defined
        if(dto.getId() == null){
            room = roomDao.save(new Room(building, dto.getName(), dto.getFloor(), dto.getCurrentTemperature(), dto.getTargetTemperature()));
        }
        else {
            room = roomDao.findById(dto.getId()).orElseThrow(IllegalArgumentException::new);
            room.setFloor(dto.getFloor());
            room.setName(dto.getName());
            room.setCurrentTemperature(dto.getCurrentTemperature());
            room.setTargetTemperature(dto.getTargetTemperature());
        }

        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);

        for (Heater heater:room.getHeaters()) {
            heaterDao.deleteById(heater.getId());
        }

        for (Window window:room.getWindows()) {
            windowDao.deleteById(window.getId());
        }

        roomDao.deleteById(id);
    }

    @PutMapping(path = "/{id}/switchWindows")
    public void switchWindows(@PathVariable Long id) {
        Room room  = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);

        for (Window window:room.getWindows()) {
            window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED : WindowStatus.OPEN);
        }
    }

    @PutMapping(path = "/{id}/switchHeaters")
    public void switchHeaters(@PathVariable Long id) {
        Room room  = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);

        for (Heater heater:room.getHeaters()) {
            heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON);
        }
    }
}
