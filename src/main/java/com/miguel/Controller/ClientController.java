package com.miguel.Controller;

import com.miguel.model.Client;
import com.miguel.model.Clinic;
import com.miguel.model.ClinicAndClient;
import com.miguel.service.ClientService;
import com.miguel.service.ClinicAndClientService;
import com.miguel.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClinicAndClientService clinicAndClientService;
    private final ClinicService clinicService;

    @Autowired
    public ClientController(ClientService clientService, ClinicAndClientService clinicAndClientService, ClinicService clinicService) {
        this.clientService = clientService;
        this.clinicAndClientService = clinicAndClientService;
        this.clinicService = clinicService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client, @RequestParam Long clinicId) {
        // Crear el cliente
        Client createdClient = clientService.createClient(client);

        // Obtener la clínica asociada
        Optional<Clinic> clinicOptional = clinicService.getClinicById(clinicId);
        if (!clinicOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Clinic clinic = clinicOptional.get(); // Extraemos el Clinic del Optional

        // Crear la relación en ClinicAndClient
        ClinicAndClient clinicAndClient = new ClinicAndClient();
        clinicAndClient.setClient(createdClient);
        clinicAndClient.setClinic(clinic);
        clinicAndClientService.saveClinicAndClient(clinicAndClient);

        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/clients/{adminId}")
    public List<Object[]> getClientsByAdminId(@PathVariable Long adminId) {
        return clientService.getClientsByAdminId(adminId);
    }
    @GetMapping("/by-employee/{employeeId}")
    public List<Object[]> getClientsByEmployeeId(@PathVariable Long employeeId) {
        return clientService.getClientsByEmployeeId(employeeId);
    }
}
