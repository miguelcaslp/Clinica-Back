package com.miguel.service;

import com.miguel.UTILS.Encrypt;
import com.miguel.UTILS.YourJwtUtil;
import com.miguel.model.Admin;
import com.miguel.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private YourJwtUtil jwtUtil;  // Inyectamos la utilidad para generar el token

    // Método de login modificado para devolver un token
    public Admin login(String email, String password) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();

            try {
                String encryptedPassword = Encrypt.Encrypt(password);  // Encriptamos la contraseña proporcionada
                if (admin.getPassword().equals(encryptedPassword)) {
                    return admin;  // Devuelve el objeto Admin
                } else {
                    throw new InvalidCredentialsException("Contraseña incorrecta.");
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("No existe un administrador con el correo: " + email);
        }
    }


    // Excepciones de login
    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }




    public class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id " + id));
    }

    public Admin createAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Ya existe un administrador con el correo electrónico: " + admin.getEmail());
        }
        try {
            String encryptedPassword = Encrypt.Encrypt(admin.getPassword());
            admin.setPassword(encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
        }
        return adminRepository.save(admin);
    }



    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
