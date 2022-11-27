package com.outlaw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    private Long id;
    private String name;
    private String lastName;
}

public class Main {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1L, "John", "Doe");

        SuperPacket packet = SuperPacket.create(2); // пакет standard
        packet.setValue(1, mapper.writeValueAsString(user), User.class);

        SuperPacket packet1 = SuperPacket.parse(packet.toByteArray());
        String json = packet1.getValue(1, String.class);
        User user1 = mapper.readValue(json, User.class);
        System.out.println(user1);


        SuperPacket encryptedPacket = SuperPacket.create("bar12345bar12345"); // подпакет 2 пакета шифрованный, принимает 16-ти значный ключ шифрования
        encryptedPacket.setValue(1, mapper.writeValueAsString(user), User.class);

        SuperPacket decryptedPacket = SuperPacket.parse(encryptedPacket.toByteArray());
        System.out.println(decryptedPacket.getValue(1, String.class));

        // пакеты типа 1 и 4 handshake и goodbye соответсвенно

    }
}
