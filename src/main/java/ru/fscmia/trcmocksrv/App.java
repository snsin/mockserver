package ru.fscmia.trcmocksrv;


import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App {
    private static final int MINIMUM_READED_LNG = 15;

    public static void main(@NotNull String[] args) {
        Stream<String> arguments = Stream.of(args);
        int portNumber  = Integer.valueOf(arguments.findFirst().orElse("60000"));
        System.out.println("Hello World!\n" +  portNumber + "\n");
        byte[] buffer = new byte[10240];

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            InputStream byteStream = clientSocket.getInputStream();
        ) {
            while (!clientSocket.isClosed()) {
                int recievedLength =  byteStream.available();
                if (recievedLength > MINIMUM_READED_LNG) {
                    int readed = byteStream.read(buffer);

                    System.out.println(Hex.encodeHexString(Arrays.copyOf(buffer, readed)) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
