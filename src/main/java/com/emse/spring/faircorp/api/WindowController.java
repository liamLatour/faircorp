package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/** Allows to manipulate windows from an API.
 * @author Liam LATOUR
 * @version 1.5
 */
@RestController
@RequestMapping("/api/windows")
@Transactional
public class WindowController {
    private final WindowDao windowDao;
    private final RoomDao roomDao;

    private static Logger logger = LogManager.getLogger(WindowController.class);

    /**
     * Constructor for the class
     * @param windowDao DAO associated with the window
     * @param roomDao DAO associated with the room the window is attached to
     */
    public WindowController(WindowDao windowDao, RoomDao roomDao) { // (4)
        this.windowDao = windowDao;
        this.roomDao = roomDao;
    }

    /**
     * @return List of all windows
     */
    @GetMapping
    public List<WindowDto> findAll() {
        logger.debug("Accessed list of windows");
        return windowDao.findAll().stream().map(WindowDto::new).collect(Collectors.toList());
    }

    /**
     * @param id Id of a window
     * @return The window
     */
    @GetMapping(path = "/{id}")
    public WindowDto findById(@PathVariable Long id) {
        return windowDao.findById(id).map(WindowDto::new).orElse(null);
    }

    /**
     * Used to open or close window
     * @param id Id of a window
     * @return The window
     */
    @PutMapping(path = "/{id}/switch")
    public WindowDto switchStatus(@PathVariable Long id) {
        Window window = windowDao.findById(id).orElseThrow(IllegalArgumentException::new);
        window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED: WindowStatus.OPEN);
        return new WindowDto(window);
    }

    /**
     * Creates a window
     * @param dto window DTO
     * @return The created window
     */
    @PostMapping
    public WindowDto create(@RequestBody WindowDto dto) {
        // WindowDto must always contain the window room
        Room room = roomDao.getReferenceById(dto.getRoomId());
        Window window = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            window = windowDao.save(new Window(room, dto.getName(), dto.getWindowStatus()));
        }
        else {
            logger.info("A window was modified");
            window = windowDao.getReferenceById(dto.getId());
            window.setWindowStatus(dto.getWindowStatus());
            window.setName(dto.getName());
        }

        return new WindowDto(window);
    }

    /**
     * Deletes a window
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        if (id == null){
            logger.error("The given id for window deletion was null");
        }
        windowDao.deleteById(id);
    }
}
