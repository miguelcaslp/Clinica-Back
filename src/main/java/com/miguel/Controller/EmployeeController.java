package com.miguel.Controller;

import com.miguel.UTILS.YourJwtUtil;
import com.miguel.model.Employee;
import com.miguel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private YourJwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(500).build(); // Error en la encriptación
        }
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            if (updatedEmployee != null) {
                return ResponseEntity.ok(updatedEmployee);
            }
            return ResponseEntity.notFound().build();
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(500).build(); // Error en la encriptación
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/admin/{adminId}")
    public List<Employee> getEmployeesByAdminId(@PathVariable Long adminId) {
        return employeeService.getEmployeesByAdminId(adminId);
    }
    @GetMapping("/clinic/{employeeId}")
    public List<Employee> getAllEmployeesByEmployeeId(@PathVariable Long employeeId) {
        return employeeService.getAllEmployeesByEmployeeId(employeeId);
    }
    @GetMapping("/byAdmin/{adminId}")
    public List<Employee> getEmployeesByAdmin(@PathVariable Long adminId) {
        return employeeService.getEmployeesByAdminOrdered(adminId);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Employee employee) {
        try {
            // Autenticación exitosa, obtener el objeto Employee
            Employee loggedInEmployee = employeeService.login(employee.getEmail(), employee.getPassword());

            // Genera el token con el ID del Employee
            String token = jwtUtil.generateToken(loggedInEmployee.getId());

            // Responder con el token y la información del empleado (id y categoría)
            Map<String, Object> response = Map.of(
                    "success", true,
                    "employeeId", loggedInEmployee.getId(),
                    "category", loggedInEmployee.getCategory(),
                    "token", token
            );
            return ResponseEntity.ok(response);
        } catch (EmployeeService.InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas."));
        } catch (EmployeeService.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Empleado no encontrado."));
        }
    }
}
