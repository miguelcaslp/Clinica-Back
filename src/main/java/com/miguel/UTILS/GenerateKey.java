package com.miguel.UTILS;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateKey {
    public static void main(String[] args) throws Exception {
        // Crear un generador de claves para HmacSHA256
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // Tamaño de la clave en bits
        SecretKey secretKey = keyGen.generateKey();

        // Convertir la clave a Base64
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("JWT Secret Key: " + base64Key);
    }
}
