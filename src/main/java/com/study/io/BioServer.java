package com.study.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 用BIO方式让客户端连接程序，监听服务端
 */
public class BioServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("BIOServer has started，Listening on port" + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection from " + clientSocket.getRemoteSocketAddress());
                // BIO阻塞原因：getInputStream阻塞一直占用当前线程资源，不让当前线程做其他事情
                Scanner input = new Scanner(clientSocket.getInputStream());
                //针对每个socket，不断的进行数据交互
                while (true) {
                    String request = input.nextLine();
                    if ("quit".equals(request)) {
                        break;
                    }
                    System.out.println(String.format("From %s : %s", clientSocket.getRemoteSocketAddress(), request));
                    String response = "From BIOServer Hello " + request + ".\n";
                    clientSocket.getOutputStream().write(response.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
