package org.example;

import org.example.AwesomePacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class AwesomeServer implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Integer port;
    private InputStream reader;
    private OutputStream writer;
    private ThreadPoolExecutor serverPool;
    private final byte bPoleSize = 3;
    private String[] pole = new String[bPoleSize * bPoleSize];
    private byte bPlayerNum = 1;

    private AwesomeServer() {
    }

    public static AwesomeServer create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        AwesomeServer server = new AwesomeServer();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        return server;
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

    public void run() {
        try {
            clientSocket = serverSocket.accept();
            reader = clientSocket.getInputStream();
            writer = clientSocket.getOutputStream();

            while (true) {
                start();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

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
                System.out.println("\n " + bPlayerNum + " player's turn");
                showPole(); // Рисуем поле
                System.out.print("Type a digit where you want to put " + (1 == bPlayerNum ? "cross" : "zero") + ": ");
                if (sc.hasNextInt()) { // проверяем, есть ли в потоке целое число
                    iTmp = sc.nextInt() - 1; // считывает целое число с потока ввода и сохраняем в переменную
                    if (isValidInput(iTmp))
                        break;
                }
                System.out.println("You have entered invalid digit, please try again");
                sc.next();
            }
            try {
                putX(iTmp); // Вставляем на поле крестик или нолик

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(writer);
                objectOutputStream.writeObject(pole);
                objectOutputStream.flush();

                ObjectInputStream objectInputStream = new ObjectInputStream(reader);

                pole = (String[]) objectInputStream.readObject();

                bPlayerNum = 1;

            } catch (Exception e) {
                System.out.println("Something went wrong ;(");
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
            for (j = 0; j < bPoleSize - 1; j++) {
                bRowWin &= (getXY(i, j).charAt(0) == getXY(i, j + 1).charAt(0));
                bColWin &= (getXY(j, i).charAt(0) == getXY(j + 1, i).charAt(0));
            }
            if (bColWin || bRowWin) {
                System.out.println("Zero wins");
                System.exit(0);
                return true;
            }
        }

        // Проверка победы по диагоналям
        bRowWin = true;
        bColWin = true;
        for (i = 0; i < bPoleSize - 1; i++) {
            bRowWin &= (getXY(i, i).charAt(0) == getXY(i + 1, i + 1).charAt(0));
            bColWin &= (getXY(i, bPoleSize - i - 1).charAt(0) == getXY(i + 1, bPoleSize - i - 2).charAt(0));
        }
        if (bColWin || bRowWin) {
            System.out.println("Zero wins");
            System.exit(0);
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
        if (bPoleSize * bPoleSize <= i) {
            System.out.println("Draw");
            System.exit(0);

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
}
