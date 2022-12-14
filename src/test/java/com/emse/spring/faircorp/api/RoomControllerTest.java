package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RoomController.class)
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeaterDao heaterDao;
    @MockBean
    private WindowDao windowDao;
    @MockBean
    private BuildingDao buildingDao;
    @MockBean
    private RoomDao roomDao;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadRooms() throws Exception {
        given(roomDao.findAll()).willReturn(List.of(
                createRoom("room 1"),
                createRoom("room 2")
        ));

        mockMvc.perform(get("/api/rooms").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name")
                        .value(containsInAnyOrder("room 1", "room 2")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadARoomAndReturnNullIfNotFound() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP responseBuilding building, String name, Integer floor,
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldLoadARoom() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.of(createRoom("room 1")));

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");
        expectedRoom.setId(1L);
        expectedRoom.setFloor(5);
        expectedRoom.setTargetTemperature(1.0);
        expectedRoom.setCurrentTemperature(5.0);
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedRoom.getBuilding());
        given(roomDao.getReferenceById(anyLong())).willReturn(expectedRoom);

        mockMvc.perform(post("/api/rooms")
                        .with(csrf())
                        .content(json)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("room 1"))
                .andExpect(jsonPath("$.targetTemperature").value("1.0"))
                .andExpect(jsonPath("$.currentTemperature").value("5.0"))
                .andExpect(jsonPath("$.floor").value("5"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldCreateRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");
        expectedRoom.setId(null);
        expectedRoom.setFloor(5);
        expectedRoom.setTargetTemperature(1.0);
        expectedRoom.setCurrentTemperature(5.0);
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedRoom.getBuilding());
        given(roomDao.save(any())).willReturn(expectedRoom);

        mockMvc.perform(
                        post("/api/rooms")
                                .accept(APPLICATION_JSON)
                                .with(csrf())
                                .content(json)
                                .contentType(APPLICATION_JSON_VALUE))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.targetTemperature").value("1.0"))
                .andExpect(jsonPath("$.currentTemperature").value("5.0"))
                .andExpect(jsonPath("$.floor").value("5"))
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteRoom() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.of(createRoom("room 1")));

        mockMvc.perform(delete("/api/rooms/999").with(csrf()))
                .andExpect(status().isOk());
    }

    private Room createRoom(String name) {
        Building building = new Building();
        return new Room(building, name, 1, 0.0, 10.0);
    }

}