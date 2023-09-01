package com.study.io;

import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private static RequestHandler requestHandler;
    private static Socket clientSocket;

    public ClientHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (Scanner input = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                String request = input.nextLine();
                if ("quit".equals(request)) {
                    break;
                }
                System.out.println(String.format("From %s : %s", clientSocket.getRemoteSocketAddress(), request));
                String response = "From BIOServer Hello " + request + ".\n";
                clientSocket.getOutputStream().write(response.getBytes());
            }
        } catch (Exception e) {

        }
    }
}
