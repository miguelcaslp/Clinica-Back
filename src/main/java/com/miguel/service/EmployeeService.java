package com.miguel.service;

import com.miguel.model.Employee;
import com.miguel.repository.EmployeeRepository;
import com.miguel.UTILS.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) throws NoSuchAlgorithmException {
        // Encriptar la contraseña antes de guardar
        employee.setPassword(Encrypt.Encrypt(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) throws NoSuchAlgorithmException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setPhoneNumber(employeeDetails.getPhoneNumber());
            employee.setCategory(employeeDetails.getCategory());
            // Solo actualizar la contraseña si se proporciona una nueva
            if (employeeDetails.getPassword() != null && !employeeDetails.getPassword().isEmpty()) {
                employee.setPassword(Encrypt.Encrypt(employeeDetails.getPassword()));
            }
            return employeeRepository.save(employee);
        }
        return null;
    }
    public List<Employee> getEmployeesByAdminId(Long adminId) {
        return employeeRepository.findAllEmployeesByAdminId(adminId);
    }
    public List<Employee> getEmployeesByAdminOrdered(Long adminId) {
        return employeeRepository.findEmployeesByAdminIdOrdered(adminId);
    }
    public List<Employee> getAllEmployeesByEmployeeId(Long employeeId) {
        return employeeRepository.findAllEmployeesByEmployeeId(employeeId);
    }
    // Método de login para el empleado
    public Employee login(String email, String password) {
        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepository.findByEmail(email));
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            try {
                String encryptedPassword = Encrypt.Encrypt(password); // Encriptamos la contraseña proporcionada
                if (employee.getPassword().equals(encryptedPassword)) {
                    return employee; // Si las contraseñas coinciden, retornamos el empleado
                } else {
                    throw new InvalidCredentialsException("Contraseña incorrecta.");
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("No existe un empleado con el correo: " + email);
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
}
