package com.outlaw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
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
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1L, "John", "Doe");

        SuperPacket packet = SuperPacket.create(2);
        packet.setValue(1, mapper.writeValueAsString(user), User.class);

        SuperPacket parsed = SuperPacket.parse(packet.toByteArray());
        String json = (String) parsed.getValue(1, String.class);
        System.out.println(mapper.readValue(json, parsed.getClass(1)));

    }
}
