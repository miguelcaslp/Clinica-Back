package com.miguel.Controller;

import com.miguel.UTILS.YourJwtUtil;
import com.miguel.model.Admin;
import com.miguel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private YourJwtUtil jwtUtil;

    // Eliminar el login relacionado con token
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Admin admin) {
        try {
            // Autenticación exitosa, obtener el objeto Admin
            Admin loggedInAdmin = adminService.login(admin.getEmail(), admin.getPassword());

            // Genera el token con el ID del Admin
            String token = jwtUtil.generateToken(loggedInAdmin.getId());

            // Responder con el token y la información del admin
            Map<String, Object> response = Map.of("success", true, "admin", loggedInAdmin, "token", token);
            return ResponseEntity.ok(response);
        } catch (AdminService.InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas."));
        } catch (AdminService.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Administrador no encontrado."));
        }
    }



    // Métodos restantes, sin cambios
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        try {
            Admin createdAdmin = adminService.createAdmin(admin);
            return ResponseEntity.ok(createdAdmin);
        } catch (AdminService.EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
