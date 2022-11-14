package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BuildingDaoTest {

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindABuilding() {
        Building building = buildingDao.findById(-1L).orElseThrow(IllegalArgumentException::new);
        Assertions.assertThat(building.getName()).isEqualTo("Office");
        Assertions.assertThat(building.getAddress()).isEqualTo("12 Kentucky road");
    }

    @Test
    public void shouldFindABuildingByAddress() {
        List<Building> buildings = buildingDao.findByNameOrAddress("12 Kentucky road");
        Assertions.assertThat(buildings)
                .hasSize(1)
                .extracting(Building::getId, Building::getName)
                .containsExactlyInAnyOrder(Tuple.tuple(-1L, "Office"));
    }

    @Test
    public void shouldFindABuildingByName() {
        List<Building> buildings = buildingDao.findByNameOrAddress("Office");
        Assertions.assertThat(buildings)
                .hasSize(1)
                .extracting(Building::getId, Building::getAddress)
                .containsExactlyInAnyOrder(Tuple.tuple(-1L, "12 Kentucky road"));
    }
}
