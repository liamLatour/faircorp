package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    private final BuildingDao buildingDao;
    private final RoomDao roomDao;

    public BuildingController(BuildingDao buildingDao, RoomDao roomDao) {
        this.buildingDao = buildingDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;

        // On creation id is not defined
        if(dto.getId() == null){
            building = buildingDao.save(new Building(dto.getName(), dto.getAddress()));
        }
        else {
            building = buildingDao.getReferenceById(dto.getId());
            building.setAddress(dto.getAddress());
            building.setName(dto.getName());
        }

        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Building building = buildingDao.findById(id).orElseThrow(IllegalArgumentException::new);

        if(building.getRooms() != null){
            for (Room room:building.getRooms()) {
                roomDao.deleteById(room.getId());
            }
        }

        buildingDao.deleteById(id);
    }

    @GetMapping(path = "/search")
    public List<BuildingDto> findByNameOrAddress(@RequestParam String name) {
        return buildingDao.findByNameOrAddress(name).stream().map(BuildingDto::new).collect(Collectors.toList());
    }

}
