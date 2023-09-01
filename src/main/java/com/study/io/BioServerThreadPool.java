package com.study.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServerThreadPool {

    Map<Socket, String> map;

    public static void main(String[] args) {
        // 创建3个线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        RequestHandler requestHeader = new RequestHandler();
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("BIO thread Server has started，Listening on port" + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // 线程提交
                executor.submit(new ClientHandler(clientSocket, requestHeader));
                System.out.println("Connection from " + clientSocket.getRemoteSocketAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
