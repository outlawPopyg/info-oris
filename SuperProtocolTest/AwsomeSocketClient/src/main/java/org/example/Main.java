package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        AwesomeClient client = AwesomeClient.initConnection("localhost", 4444);
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = client.getReader();
        OutputStream outputStream = client.getWriter();

        byte[] data = AwesomeClient.readInput(inputStream);
        SuperPacket packet = SuperPacket.parse(data);
        System.out.println(packet.getValue(1, String.class));

        SuperPacket response = SuperPacket.create(1);
        response.setValue(1, "pong");

        outputStream.write(response.toByteArray());
        outputStream.flush();

        data = AwesomeClient.readInput(inputStream);
        SuperPacket json = SuperPacket.parse(data);

        User user = mapper.readValue(json.getValue(1, String.class), User.class);
        System.out.println(user);

        SuperPacket encrypted = SuperPacket.create("bar12345bar12345");
        encrypted.setValue(1, mapper.writeValueAsString(user));

        outputStream.write(encrypted.toByteArray());
        outputStream.flush();

        data = AwesomeClient.readInput(inputStream);
        SuperPacket goodbye = SuperPacket.parse(data);
        if (goodbye.getType() == 4) {
            System.out.println("bye");
            SuperPacket bye = SuperPacket.create(4);
            outputStream.write(bye.toByteArray());
            outputStream.flush();
        }


    }
}
