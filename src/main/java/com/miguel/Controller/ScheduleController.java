package com.miguel.Controller;

import com.miguel.model.Schedule;
import com.miguel.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(savedSchedule);
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule != null) {
            return ResponseEntity.ok(schedule);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/clinic/{clinicId}")
    public List<Schedule> getSchedulesByClinicId(@PathVariable Long clinicId) {
        return scheduleService.getSchedulesByClinicId(clinicId);
    }
    // Controlador para actualizar un horario específico
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        // Intentamos obtener el horario por ID
        Schedule existingSchedule = scheduleService.getScheduleById(id);

        if (existingSchedule == null) {
            return ResponseEntity.notFound().build();  // Si no se encuentra, devolver 404 Not Found
        }

        // Actualizamos los datos del horario
        existingSchedule.setDayOfWeek(schedule.getDayOfWeek());
        existingSchedule.setOpeningTime(schedule.getOpeningTime());
        existingSchedule.setClosingTime(schedule.getClosingTime());

        // Guardamos el horario actualizado
        Schedule updatedSchedule = scheduleService.saveSchedule(existingSchedule);

        return ResponseEntity.ok(updatedSchedule);  // Devolver el horario actualizado
    }

}
