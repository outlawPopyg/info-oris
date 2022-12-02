import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        AwesomeClient client = AwesomeClient.initConnection("localhost", 4444);
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = client.getReader();
        OutputStream outputStream = client.getWriter();
        Scanner sc = new Scanner(System.in);

        System.out.println("Сейчас вам загадают число, вам будет нужно его угадать, \n" +
                " например чтобы узнать больше ли число заданного, введите >guess, где guess - ваше предполагаемое число:");

        byte[] data = AwesomeClient.readInput(inputStream);
        SuperPacket response = SuperPacket.parse(data);
        Message message = mapper.readValue(response.getValue(1, String.class), Message.class);
        System.out.println(CezarCod.getCodingMessage(message.getMessage(), -message.getKey()));

        while (true) {

            String guess = sc.next();
            SuperPacket guessPacket = SuperPacket.create(1);
            guessPacket.setValue(1, guess);

            outputStream.write(guessPacket.toByteArray());
            outputStream.flush();


            byte[] responseData = AwesomeClient.readInput(inputStream);
            SuperPacket responsePacket = SuperPacket.parse(responseData);
            if (responsePacket.getType() == 5) {
                System.out.println("Right guess");
            } else if (responsePacket.getType() == 6) {
                System.out.println("Wrong guess");
            } else if (responsePacket.getType() == 7) {
                System.out.println("You win!");
                break;
            }

        }

    }
}
