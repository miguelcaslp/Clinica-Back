package com.miguel.service;

import com.miguel.model.Client;
import com.miguel.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }



    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
    public List<Object[]> getClientsByAdminId(Long adminId) {
        return clientRepository.findClientsByAdminId(adminId);
    }
    public List<Object[]> getClientsByEmployeeId(Long employeeId) {
        return clientRepository.findClientsByEmployeeId(employeeId);
    }
}
