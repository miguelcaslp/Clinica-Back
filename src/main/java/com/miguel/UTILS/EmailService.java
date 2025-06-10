package com.miguel.UTILS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendConfirmationEmail(String to, String clientName) {
        String token = generateToken(); // Generar un token de confirmación único
        String confirmationLink = "http://localhost:8080/clients/confirm?email=" + to + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmación de Acceso a sus Coches");
        message.setText("Estimado " + clientName + ",\n\nPor favor confirme su acceso a los datos de sus coches usando el siguiente enlace: "
                + confirmationLink + "\n\nSaludos,\nSu Empresa");
        emailSender.send(message);

        // Aquí podrías guardar el token en la base de datos o en una caché asociada al cliente
    }

    private String generateToken() {
        // Generar un token aleatorio (puedes usar UUID o cualquier otro método)
        return UUID.randomUUID().toString();
    }
}
