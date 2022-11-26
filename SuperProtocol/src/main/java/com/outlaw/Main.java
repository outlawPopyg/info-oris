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
    public static void main(String[] args)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1L, "John", "Doe");

        SuperPacket packet = SuperPacket.create(2);
        packet.setValue(1, mapper.writeValueAsString(user), User.class);


        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec("bar12345bar12345".getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] bytes = cipher.doFinal(packet.toByteArray());

        Cipher dec = Cipher.getInstance("AES");
        dec.init(Cipher.DECRYPT_MODE, key);
        byte[] decb = dec.doFinal(bytes);

        SuperPacket decrypted = SuperPacket.parse(decb);
        System.out.println(decrypted.getClass(1));


    }
}
