package org.example;

import org.example.base.AwesomePacket;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class AwesomeClient {
    private String host;
    private Integer port;
    private Socket socket;
    private InputStream reader;
    private OutputStream writer;

    private final byte bPoleSize = 3;
    private String[] pole = new String[bPoleSize * bPoleSize];
    private byte bPlayerNum = 0;

    private AwesomeClient() {}
    public static AwesomeClient initConnection(String host, Integer port) throws IOException {
        AwesomeClient client = new AwesomeClient();
        client.port = port;
        client.host = host;
        client.socket = new Socket(host, port);
        client.reader = client.socket.getInputStream();
        client.writer = client.socket.getOutputStream();
        return client;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] extendArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, extendArray, 0, oldSize);
        return extendArray;
    }

    private byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) != -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && AwesomePacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }

        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);

        return data;
    }

    public void sendMessage(AwesomePacket packet) throws IOException {
        writer.write(packet.toByteArray());
        writer.flush();

    }

    public static void main(String[] args) throws IOException {
        AwesomeClient client = AwesomeClient.initConnection("localhost", 4444);
        client.start();
    }

    public synchronized void start() throws IOException {

        int iTmp = 0;
        Scanner sc = new Scanner(System.in);

        // Инициализация поля
        for (int i = 0; i < bPoleSize * bPoleSize; i++)
            pole[i] = Integer.toString(++iTmp);

        // Играем, пока не наступит конец игры
        while (!isGameEnd()) {
            nextPlayer();
            while (true) {
                System.out.println("\nХод игрока " + bPlayerNum);
                showPole(); // Рисуем поле
                System.out.print("Наберите число, куда вы хотите вставить " + (1 == bPlayerNum ? "крестик" : "нолик") + ": ");
                if (sc.hasNextInt()) { // проверяем, есть ли в потоке целое число
                    iTmp = sc.nextInt() - 1; // считывает целое число с потока ввода и сохраняем в переменную
                    if (isValidInput(iTmp))
                        break;
                }
                System.out.println("Вы ввели неправильное число. Повторите ввод");
                sc.next();
            }
            try {

                putX(iTmp); // Вставляем на поле крестик или нолик
                AwesomePacket packet = AwesomePacket.create(1);
                packet.setValue(1, pole);
                sendMessage(packet);

                byte[] data = readInput(getReader());
                AwesomePacket response = AwesomePacket.parse(data);

                if (response.getType() == 2) {
                    System.exit(0);
                }

                if (response.getType() == 3) {
                    pole = response.getValue(1, String[].class);
                    bPlayerNum = 0;
                }

            } catch (Exception e) {
                System.out.println("Что-то пошло не так ;(");
            }
        }
        showPole();
    }

    private boolean isValidInput(int iIn) {
        if (iIn >= bPoleSize * bPoleSize) return false;
        if (iIn < 0) return false;
        switch (getX(iIn)) {
            case 'O':
            case 'X':
                return false;
        }

        return true;
    }

    private void nextPlayer() {
        bPlayerNum = (byte) (1 == bPlayerNum ? 2 : 1);
    }

    private boolean isGameEnd() throws IOException {
        int i, j;
        boolean bRowWin = false, bColWin = false;

        // Проверка победы на колонках и столбиках
        for (i = 0; i < bPoleSize; i++) {
            bRowWin = true;
            bColWin = true;
            for (j = 0; j < bPoleSize-1; j++) {
                bRowWin &= (getXY(i,j).charAt(0) == getXY(i,j+1).charAt(0));
                bColWin &= (getXY(j,i).charAt(0) == getXY(j+1,i).charAt(0));
            }
            if (bColWin || bRowWin) {
                System.out.println("Победил игрок " + bPlayerNum);
                AwesomePacket packet = AwesomePacket.create(2);
                writer.write(packet.toByteArray());
                writer.flush();

                return true;
            }
        }

        // Проверка победы по диагоналям
        bRowWin = true;
        bColWin = true;
        for (i=0; i<bPoleSize-1; i++) {
            bRowWin &= (getXY(i,i).charAt(0) == getXY(i+1,i+1).charAt(0));
            bColWin &= (getXY(i, bPoleSize-i-1).charAt(0) == getXY(i+1, bPoleSize-i-2).charAt(0));
        }
        if (bColWin || bRowWin) {
            System.out.println("Победил игрок " + bPlayerNum);
            AwesomePacket packet = AwesomePacket.create(2);
            writer.write(packet.toByteArray());
            writer.flush();

            return true;
        }

        // Проверка существования новых ходов
        for (i = 0; i < bPoleSize * bPoleSize; i++) {
            switch (getX(i)) {
                case 'O':
                case 'X':
                    break;
                default:
                    return false;
            }
        }
        if (bPoleSize*bPoleSize <= i) {
            System.out.println("Ничья. Кончились ходы.");
            AwesomePacket packet = AwesomePacket.create(2);
            writer.write(packet.toByteArray());
            writer.flush();
            return true;
        }

        // Продолжаем игру
        return false;
    }
    private String getXY(int x, int y) {
        return pole[x * bPoleSize + y];
    }

    private char getX(int x) {
        return pole[x].charAt(0);
    }

    private void putX(int x) {
        pole[x] = 1 == bPlayerNum ? "X" : "O";
    }

    private void showPole() {
        for (int i = 0; i < bPoleSize; i++) {
            for (int j = 0; j < bPoleSize; j++) {
                System.out.printf("%4s", getXY(i, j));
            }
            System.out.print("\n");
        }
    }

    public InputStream getReader() {
        return reader;
    }
}
